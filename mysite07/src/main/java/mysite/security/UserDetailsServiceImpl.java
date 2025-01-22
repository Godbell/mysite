package mysite.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) {
        UserVo userVo = userRepository.findByEmail(email);

        return new ObjectMapper().convertValue(userVo, UserDetailsImpl.class);
    }
}
