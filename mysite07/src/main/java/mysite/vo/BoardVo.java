package mysite.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoardVo {
    private List<PostVo> posts;
    private Integer totalCount;
    private Integer postsCountPerPage;
    private Integer currentPage;
}
