package mysite.vo;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteMetadata {
    private String title;

    public SiteMetadata(@Value("${site.metadata.title:Default none}") String title) {

        this.title = title;
    }
}
