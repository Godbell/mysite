package mysite.vo;

import java.util.List;
public class BoardVo {
    private List<PostVo> posts;
    private Integer totalCount;
    private Integer postsCountPerPage;
    private Integer currentPage;

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

    public Integer getPostsCountPerPage() {
        return postsCountPerPage;
    }

    public void setPostsCountPerPage(Integer postsCountPerPage) {
        this.postsCountPerPage = postsCountPerPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
}
