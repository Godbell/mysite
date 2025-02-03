package mysite.repository;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;
import mysite.vo.UserVo;

@Repository
@AllArgsConstructor
public class UserRepository {
    private final SqlSession sqlSession;

    public int insert(UserVo vo) {
        return sqlSession.insert("user.insert", vo);
    }

    public UserVo findByEmailAndPassword(String email, String password) {
        return sqlSession.selectOne(
            "user.findByEmailAndPassword",
            Map.of(
                "email", email,
                "password", password
            )
        );
    }

    public void update(UserVo vo) {
        sqlSession.update("user.update", vo);
    }

    public UserVo findById(Long id) {
        return sqlSession.selectOne("user.findById", id);
    }

    public boolean isEmailAvailable(String email) {
        return (int)sqlSession.selectOne("user.isEmailAvailable", email) == 0;
    }

    public UserVo findByEmail(String email) {
        return sqlSession.selectOne("user.findByEmail", email);
    }
}
