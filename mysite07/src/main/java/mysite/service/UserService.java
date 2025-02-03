package mysite.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import mysite.dto.UserJoinRequestDto;
import mysite.dto.UserModifyRequestDto;
import mysite.exception.BadRequestException;
import mysite.repository.UserRepository;
import mysite.vo.UserVo;

@Service
@AllArgsConstructor
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final UserDetailsService userDetailsService;

    public void createUser(UserJoinRequestDto dto) {
        if (!userRepository.isEmailAvailable(dto.getEmail())) {
            throw new BadRequestException();
        }

        UserVo vo = new UserVo();
        vo.setName(dto.getName());
        vo.setEmail(dto.getEmail());
        vo.setGender(dto.getGender());

        vo.setPassword(dto.getPassword());
        vo.setPassword(passwordEncoder.encode(dto.getPassword()));

        userRepository.insert(vo);
    }

    public void update(
        Long id, UserModifyRequestDto dto, Authentication currentAuthentication
    ) {
        UserVo vo = new UserVo();
        vo.setId(id);
        vo.setPassword(
            (dto.getPassword() != null && !dto.getPassword().isEmpty())
                ? passwordEncoder.encode(dto.getPassword())
                : null
        );
        vo.setName(
            (dto.getName() != null && !dto.getName().isEmpty())
                ? dto.getName()
                : null
        );
        vo.setGender(
            (dto.getGender() != null && !dto.getGender().isEmpty())
                ? dto.getGender()
                : null
        );

        userRepository.update(vo);

        UserDetails userDetails = userDetailsService.loadUserByUsername(
            currentAuthentication.getName()
        );

        UsernamePasswordAuthenticationToken token =
            new UsernamePasswordAuthenticationToken(
                userDetails, currentAuthentication.getCredentials(), userDetails.getAuthorities()
            );

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public boolean getEmailAvailability(String email) {
        return userRepository.isEmailAvailable(email);
    }

    public UserVo getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
