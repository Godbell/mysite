package mysite.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GuestbookVo {
    private Long id;
    private String name;
    private String password;
    private String regDate;
    private String contents;
    private Integer index;
}
