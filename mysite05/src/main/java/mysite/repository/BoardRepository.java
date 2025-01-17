package mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import mysite.vo.BoardVo;
import mysite.vo.PostVo;

@Repository
public class BoardRepository {
    private final SqlSession sqlSession;

    public BoardRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public BoardVo findAll(Integer page, int postsCountPerPage, String searchKeyword) {
        BoardVo result = new BoardVo();

        int currentPage = page == null ? 1 : page;
        int offset = (currentPage - 1) * postsCountPerPage;
        String query = "%" + (searchKeyword == null ? "" : searchKeyword) + "%";

        List<PostVo> posts = sqlSession.selectList(
            "board.findAll",
            Map.of(
                "offset", offset,
                "limit", postsCountPerPage,
                "query", query
            )
        );
        int totalCount = sqlSession.selectOne("board.totalCount", query);

        result.setPosts(posts);
        result.setPostsCountPerPage(postsCountPerPage);
        result.setCurrentPage(currentPage);
        result.setTotalCount(totalCount);

        return result;
    }

    public PostVo findByIdAndUserId(Long id, Long userId) {
        return sqlSession.selectOne(
            "board.findByIdAndUserId",
            Map.of(
                "postId", id,
                "userId", userId
            )
        );
    }

    public PostVo findById(Long id) {
        return sqlSession.selectOne("board.findById", id);
    }

    @Transactional
    public PostVo insert(PostVo vo) {
        boolean isReply = vo.getParentPostId() != null;
        PostVo parentPostVo = isReply ? findById(vo.getParentPostId()) : null;

        if (isReply && parentPostVo == null) {
            System.out.println("invalid parent post");
            return null;
        }

        if (isReply) {
            sqlSession.update(
                "board.updateIndex",
                Map.of(
                    "groupNo", parentPostVo.getGroupNo(),
                    "orderNo", parentPostVo.getOrderNo() + 1
                )
            );

            vo.setGroupNo(parentPostVo.getGroupNo());
            vo.setOrderNo(parentPostVo.getOrderNo() + 1);
            vo.setDepth(parentPostVo.getDepth() + 1);
        }

        sqlSession.insert("board.insert", vo);
        return vo;
    }

    public void update(PostVo vo) {
        sqlSession.update("board.update", vo);
    }

    public void deleteByIdAndUserId(Long postId, Long userId) {
        sqlSession.delete(
            "board.delete",
            Map.of(
                "postId", postId,
                "userId", userId
            )
        );
    }

    public void increaseHitByPostId(Long postId) {
        sqlSession.update("board.increaseHitByPostId", postId);
    }
}
