package mysite.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void createUser(UserVo vo) {
        vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        userRepository.insert(vo);
    }

    // deprecated
    public UserVo getUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public UserVo getUser(Long id) {
        return userRepository.findById(id);
    }

    public void update(UserVo vo) {
        if (vo.getPassword() != null) {
            vo.setPassword(passwordEncoder.encode(vo.getPassword()));
        }

        userRepository.update(vo);
    }

    public boolean getEmailAvailability(String email) {
        return userRepository.isEmailAvailable(email);
    }

    public void signIn(String email, String password, HttpSession session) {
        UserVo user = userRepository.findByEmailAndPassword(email, password);

        if (user == null) {
            throw new RuntimeException("login failed");
        }

        session.setAttribute("authUser", user);
    }

    public UserVo findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
