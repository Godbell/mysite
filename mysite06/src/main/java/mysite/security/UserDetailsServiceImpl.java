package mysite.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fasterxml.jackson.databind.ObjectMapper;

import mysite.repository.UserRepository;
import mysite.vo.UserVo;
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserVo userVo = userRepository.findByEmail(email);

        return new ObjectMapper().convertValue(userVo, UserDetailsImpl.class);
    }
}
