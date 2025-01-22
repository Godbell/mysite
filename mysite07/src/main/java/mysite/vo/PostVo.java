package mysite.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostVo {
    private Long id;
    private String title;
    private String contents;
    private int hit;
    private String regDate;
    private Integer groupNo;
    private Integer orderNo;
    private Integer depth;
    private Long userId;
    private String username;
    private Integer index;
    private Long parentPostId;
}
