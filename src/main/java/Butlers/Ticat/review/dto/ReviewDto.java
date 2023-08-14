package Butlers.Ticat.review.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class ReviewDto {

    @Getter
    public static class PostPatch {
        private String content;
        private double rating;
    }

    @Getter
    @Builder
    public static class ResponseInFestival{
        private long reviewId;
        private long festivalId;
        private long memberId;
        private String displayName;
        private String profileUrl;
        private String content;
        private double rating;
        private List<String> pictures;
        private int likedCount;
        private int dislikedCount;
        private int commentCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private boolean liked;
        private boolean disliked;
    }

    @Getter
    @Builder
    public static class ResponseInMyPage {
        private long reviewId;
        private long festivalId;
        private String festivalTitle;
        private String content;
        private double rating;
        private List<String> pictures;
        private int likedCount;
        private int dislikedCount;
        private int commentCount;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
    }
}
