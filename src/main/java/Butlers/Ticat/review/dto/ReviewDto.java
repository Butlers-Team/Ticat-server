package Butlers.Ticat.review.dto;

import lombok.Builder;
import lombok.Getter;

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
        int recommend;
        int unrecommended;
        int commentCount;
        List<ReviewCommentDto.Response> comments;
    }
}
