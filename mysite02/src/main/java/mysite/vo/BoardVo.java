package mysite.vo;

import java.util.List;
public class BoardVo {
    private List<PostVo> posts;
    private Integer totalCount;

    public List<PostVo> getPosts() {
        return posts.subList(0, posts.size());
    }

    public void setPosts(List<PostVo> posts) {
        this.posts = posts;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
