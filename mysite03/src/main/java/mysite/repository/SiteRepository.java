package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.SiteVo;
import mysite.web.SiteMetadata;

@Repository
public class SiteRepository {
    private final SqlSession sqlSession;

    public SiteRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public SiteVo getFull() {
        return sqlSession.selectOne("site.getFull");
    }

    public SiteMetadata getMetadata() {
        return sqlSession.selectOne("site.getMeta");
    }

    public void update(SiteVo vo) {
        sqlSession.update("site.update", vo);
    }
}
