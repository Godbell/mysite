package mysite.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserModifyRequestDto {
    @Pattern(regexp = "^$|^[a-zA-Z가-힣]{0,45}$", message = "영문 대소문자와 한글만 사용 가능합니다.")
    private String name;
    @Pattern(regexp = "^$|^[ -~]{0,30}$", message = "비밀번호는 1~30자의 문자여야 합니다.")
    private String password;
    @Pattern(regexp = "^(male|female)$", message = "성별을 선택해 주세요.")
    private String gender;
}
