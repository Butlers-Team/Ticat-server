package Butlers.Ticat.review.dto;

import lombok.Getter;

public class ReviewCommentDto {

    @Getter
    public static class PostPatch {
        private String content;
    }
}
