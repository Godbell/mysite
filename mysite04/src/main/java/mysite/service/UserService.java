package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(UserVo vo) {
        userRepository.insert(vo);
    }

    public UserVo getUser(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public UserVo getUser(Long id) {
        return userRepository.findById(id);
    }

    public void update(UserVo vo) {
        userRepository.update(vo);
    }

    public boolean getEmailAvailability(String email) {
        return userRepository.isEmailAvailable(email);
    }
}
