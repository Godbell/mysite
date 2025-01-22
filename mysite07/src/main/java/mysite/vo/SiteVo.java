package mysite.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteVo {
    private Long id;
    private String title;
    private String welcome;
    private String description;
    private String profile;
}
