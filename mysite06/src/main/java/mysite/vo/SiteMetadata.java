package mysite.vo;

import org.springframework.beans.factory.annotation.Value;
public class SiteMetadata {
    private String title;

    public SiteMetadata(@Value("${site.metadata.title:Default none}") String title) {

        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
