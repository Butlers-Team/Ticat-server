package Butlers.Ticat.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {

    @Getter
    public static class PostPatch {
        String content;
        double rating;
    }

    @Getter
    @Builder
    public static class ResponseInFestival{
        long reviewId;
        long memberId;
        String displayName;
        String profileUrl;
        String content;
        double rating;
        List<String> pictures;
        int likedCount;
        int dislikedCount;
        int commentCount;
        LocalDateTime createdAt;
        LocalDateTime modifiedAt;
        boolean liked;
        boolean disliked;
    }
}
