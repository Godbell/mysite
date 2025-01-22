package mysite.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserVo {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String password;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String gender;
    private String joinDate;
    private String role;
}
