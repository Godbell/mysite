package mysite.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import mysite.vo.GuestbookVo;

@Repository
@AllArgsConstructor
public class GuestbookRepository {
    private final SqlSession sqlSession;

    public List<GuestbookVo> findAll() {
        return sqlSession.selectList("guestbook.findAll");
    }

    public int insert(GuestbookVo vo) {
        return sqlSession.insert("guestbook.insert", vo);
    }

    public int deleteByIdAndPassword(Long id, String password) {
        return sqlSession.delete(
            "guestbook.deleteByIdAndPassword",
            Map.of(
                "id", id,
                "password", password
            )
        );
    }
}
