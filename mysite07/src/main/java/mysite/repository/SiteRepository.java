package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import mysite.vo.SiteMetadata;
import mysite.vo.SiteVo;

@Repository
@AllArgsConstructor
public class SiteRepository {
    private final SqlSession sqlSession;

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
