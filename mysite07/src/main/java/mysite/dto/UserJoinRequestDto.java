package mysite.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserJoinRequestDto {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z가-힣]{1,45}$", message = "영문 대소문자와 한글만 사용 가능합니다.")
    private String name;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    @Pattern(regexp = "^[ -~]{1,30}$", message = "비밀번호는 1~30자의 문자여야 합니다.")
    private String password;
    @NotBlank
    @Pattern(regexp = "^(male|female)$", message = "성별을 선택해 주세요.")
    private String gender;
    @NotNull
    @AssertTrue(message = "약관 동의가 필요합니다.")
    private Boolean agreeProv;
}
