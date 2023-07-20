package Butlers.Ticat.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class NaverBlogDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private String title;
        private String link;
        private String description;
        private String bloggername;
        private String postdate;
    }
}
