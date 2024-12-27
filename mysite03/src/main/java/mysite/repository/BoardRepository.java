package mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;
import mysite.vo.PostVo;

@Repository
public class BoardRepository {
    public BoardVo findAll(Integer page, int postsCountPerPage, String searchKeyword) {
        if (page == null) {
            page = 1;
        }

        BoardVo resultVo = new BoardVo();
        resultVo.setPostsCountPerPage(postsCountPerPage);
        resultVo.setCurrentPage(page);

        List<PostVo> posts = new ArrayList<>();

        String sql = """
            WITH b AS (
                SELECT board.id AS "id",
                    title, hit, g_no, depth, user_id,
                    DATE_FORMAT(board.reg_date, '%Y-%m-%d %h:%i:%s') AS reg_date_formatted,
                    name AS "username", o_no,
                    (SELECT COUNT(*) FROM board)
                        - (ROW_NUMBER() over (ORDER BY board.g_no DESC, board.o_no) - 1)
                        AS board_index
                FROM board
                LEFT JOIN webdb.user u ON board.user_id = u.id
                WHERE title LIKE ?
                ORDER BY g_no DESC, o_no
            )
                        
            """;

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement selectStatement = connection.prepareStatement(
                sql + """                   
                    SELECT
                        id, title, hit, g_no, depth, user_id, reg_date_formatted,
                        username, o_no, board_index
                    FROM b
                    LIMIT ?, ?;
                    """
            );
            PreparedStatement totalCountStatement = connection.prepareStatement(
                sql + """
                    SELECT COUNT(*) AS total_count FROM b
                    """
            )
        ) {
            String like = "%" + (searchKeyword == null ? "" : searchKeyword) + "%";

            selectStatement.setString(1, like);
            selectStatement.setInt(2, (page - 1) * postsCountPerPage);
            selectStatement.setInt(3, postsCountPerPage);

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                PostVo vo = new PostVo();
                vo.setId(resultSet.getLong("id"));
                vo.setTitle(resultSet.getString("title"));
                vo.setHit(resultSet.getInt("hit"));
                vo.setRegDate(resultSet.getString("reg_date_formatted"));
                vo.setGroupNo(resultSet.getInt("g_no"));
                vo.setDepth(resultSet.getInt("depth"));
                vo.setUserId(resultSet.getLong("user_id"));
                vo.setUsername(resultSet.getString("username"));
                vo.setOrderNo(resultSet.getInt("o_no"));
                vo.setBoardIndex(resultSet.getInt("board_index"));

                posts.add(vo);
            }
            resultVo.setPosts(posts);

            totalCountStatement.setString(1, like);
            ResultSet countResultSet = totalCountStatement.executeQuery();
            countResultSet.next();
            resultVo.setTotalCount(countResultSet.getInt("total_count"));
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }

        return resultVo;
    }

    public PostVo findByIdAndUserId(Long id, Long userId) {
        PostVo result = null;

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT board.id, title, contents
                    FROM board
                    LEFT JOIN webdb.user u ON board.user_id = u.id
                    WHERE board.id = ? AND user_id = ?;
                    """
            )
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.setLong(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = new PostVo();
                result.setId(id);
                result.setTitle(resultSet.getString("title"));
                result.setContents(resultSet.getString("contents"));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }

        return result;
    }

    public PostVo findById(Long id) {
        PostVo result = null;

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT
                        board.id, title,
                        u.id AS "user_id",
                        u.name AS "username",
                        contents, g_no, depth, o_no
                    FROM board
                    LEFT JOIN webdb.user u ON board.user_id = u.id
                    WHERE board.id = ?;
                    """
            )
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = new PostVo();
                result.setId(id);
                result.setTitle(resultSet.getString("title"));
                result.setContents(resultSet.getString("contents"));
                result.setUserId(resultSet.getLong("user_id"));
                result.setUsername(resultSet.getString("username"));
                result.setGroupNo(resultSet.getInt("g_no"));
                result.setDepth(resultSet.getInt("depth"));
                result.setOrderNo(resultSet.getInt("o_no"));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }

        return result;
    }

    public PostVo insert(PostVo vo) {
        boolean isReply = vo.getParentPostId() != null;
        PostVo parentPostVo = isReply ? findById(vo.getParentPostId()) : null;

        if (isReply && parentPostVo == null) {
            System.out.println("invalid parent post");
            return null;
        }

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement indexUpdateStatement = connection.prepareStatement("""
                UPDATE board
                    SET o_no = o_no + 1
                WHERE g_no = ?
                    AND o_no >= ?;
                """
            );
            PreparedStatement insertStatement = connection.prepareStatement(
                String.format("""
                        INSERT INTO board
                            (title, contents, user_id, o_no, g_no, depth)
                        SELECT ?, ?, ?, %s, %s, %s
                        FROM board
                        LIMIT 0, 1;
                        """,
                    isReply ? "?" : "0",
                    isReply ? "?" : "MAX(g_no) + 1",
                    isReply ? "?" : "0"
                ),
                Statement.RETURN_GENERATED_KEYS
            );
        ) {
            connection.setAutoCommit(false);

            try {
                if (isReply) {
                    // update indices only if reply, new post will be just appended
                    indexUpdateStatement.setInt(1, parentPostVo.getGroupNo());
                    indexUpdateStatement.setInt(2, parentPostVo.getOrderNo() + 1);
                    indexUpdateStatement.executeUpdate();
                }

                // add post
                insertStatement.setString(1, vo.getTitle());
                insertStatement.setString(2, vo.getContents());
                insertStatement.setLong(3, vo.getUserId());

                if (isReply) {
                    insertStatement.setInt(4, parentPostVo.getOrderNo() + 1);
                    insertStatement.setInt(5, parentPostVo.getGroupNo());
                    insertStatement.setInt(6, parentPostVo.getDepth() + 1);
                }

                insertStatement.executeUpdate();

                connection.commit();

                ResultSet generatedKeys = insertStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    vo.setId(generatedKeys.getLong(1));
                    System.out.println("set id to " + generatedKeys.getLong(1));
                }
            } catch (Exception e) {
                System.out.println("rollback transition due to error: " + e.getMessage());
                connection.rollback();
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }

        return vo;
    }

    public void update(PostVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
                UPDATE board
                    SET title = ?, contents = ?
                WHERE id = ?
                    AND user_id = ?;
                """
            )
        ) {
            preparedStatement.setString(1, vo.getTitle());
            preparedStatement.setString(2, vo.getContents());
            preparedStatement.setLong(3, vo.getId());
            preparedStatement.setLong(4, vo.getUserId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }
    }

    public void deleteById(Long postId, Long userId) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
                DELETE FROM board
                WHERE id=? AND user_id = ?;
                """
            )
        ) {
            preparedStatement.setLong(1, postId);
            preparedStatement.setLong(2, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }
    }

    public void increaseHitByPostId(Long postId) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("""
                UPDATE board SET hit = hit + 1
                WHERE id = ?;
                """
            )
        ) {
            preparedStatement.setLong(1, postId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }
    }
}
