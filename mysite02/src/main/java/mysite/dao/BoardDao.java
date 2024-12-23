package mysite.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import mysite.vo.BoardVo;
public class BoardDao {
    public List<BoardVo> findAll(int page, int postsCountPerPage) {
        List<BoardVo> result = new ArrayList<>();

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT
                        board.id, title, hit, g_no, depth, user_id,
                        DATE_FORMAT(board.reg_date, '%Y-%m-%d %h:%i:%s') AS reg_date_formatted,
                        name AS "username"
                    FROM board
                    LEFT JOIN webdb.user u ON board.user_id = u.id
                    ORDER BY g_no DESC, reg_date
                    LIMIT ?, ?;
                    """
            )
        ) {
            preparedStatement.setInt(1, page - 1);
            preparedStatement.setInt(2, postsCountPerPage);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                BoardVo vo = new BoardVo();
                vo.setId(resultSet.getLong("id"));
                vo.setTitle(resultSet.getString("title"));
                vo.setHit(resultSet.getInt("hit"));
                vo.setRegDate(resultSet.getString("reg_date_formatted"));
                vo.setGroupNo(resultSet.getInt("g_no"));
                vo.setDepth(resultSet.getInt("depth"));
                vo.setUserId(resultSet.getLong("user_id"));
                vo.setUsername(resultSet.getString("username"));

                result.add(vo);
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }

        return result;
    }

    public BoardVo findById(Long id) {
        BoardVo result = null;

        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    SELECT
                        board.id, title,
                        u.id AS "user_id",
                        u.name AS "username",
                        contents, reg_date
                    FROM board
                    LEFT JOIN webdb.user u ON board.user_id = u.id
                    WHERE board.id = ?;
                    """
            )
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                result = new BoardVo();
                result.setId(id);
                result.setTitle(resultSet.getString("title"));
                result.setContents(resultSet.getString("contents"));
                result.setUserId(resultSet.getLong("user_id"));
                result.setUsername(resultSet.getString("username"));
            }
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }

        return result;
    }

    public BoardVo insert(BoardVo vo) {
        try (
            Connection connection = DataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                """
                    INSERT INTO board
                        (title, contents, g_no, depth, user_id)
                    VALUES
                        (?, ?, ?, ?, ?);
                    """,
                Statement.RETURN_GENERATED_KEYS
            )
        ) {
            preparedStatement.setString(1, vo.getTitle());
            preparedStatement.setString(2, vo.getContents());
            preparedStatement.setInt(3, vo.getGroupNo());
            preparedStatement.setInt(4, vo.getDepth());
            preparedStatement.setLong(5, vo.getUserId());

            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();

            vo.setId(generatedKeys.getLong(1));
        } catch (SQLException e) {
            System.out.println("sql error: " + e);
        }

        return vo;
    }
}
