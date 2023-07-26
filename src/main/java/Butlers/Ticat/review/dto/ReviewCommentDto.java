package Butlers.Ticat.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class ReviewCommentDto {

    @Getter
    public static class PostPatch {
        private String content;
    }

    @Getter
    @Builder
    public static class Response {
        private long reviewCommentId;
        private long memberId;
        private String displayName;
        private String profileUrl;
        private String content;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
