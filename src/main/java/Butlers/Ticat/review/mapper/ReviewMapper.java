package Butlers.Ticat.review.mapper;

import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.review.dto.ReviewDto;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.review.entity.ReviewAddLikeInfo;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    default Review postToReview(ReviewDto.PostPatch requestBody, long memberId, long festivalId) {
        Member member = new Member();
        member.setMemberId(memberId);
        Festival festival = new Festival();
        festival.setFestivalId(festivalId);
        Review review = new Review();
        review.setMember(member);
        review.setFestival(festival);
        review.setContent(requestBody.getContent());
        review.setRate(requestBody.getRating());

        return review;
    }

    default Review patchToReview(ReviewDto.PostPatch requestBody, long memberId, long reviewId) {
        Member member = new Member();
        member.setMemberId(memberId);
        Review review = new Review();
        review.setReviewId(reviewId);
        review.setMember(member);
        review.setContent(requestBody.getContent());
        review.setRate(requestBody.getRating());

        return review;
    }

    default ReviewDto.ResponseInFestival reviewToResponseListElementInFestival(ReviewAddLikeInfo review) {
        Member member = review.getMember();
        Festival festival = review.getFestival();
        List<String> pictures = new ArrayList<>();

        for (String[] picture : review.getPictures()) {
            pictures.add(picture[0]);
        }

        return ReviewDto.ResponseInFestival.builder()
                .reviewId(review.getReviewId())
                .festivalId(festival.getFestivalId())
                .memberId(member.getMemberId())
                .displayName(member.getDisplayName())
                .profileUrl(member.getProfileUrl())
                .content(review.getContent())
                .rating(review.getRate())
                .pictures(pictures)
                .likedCount(review.getLikedCount())
                .dislikedCount(review.getDislikedCount())
                .commentCount(review.getCommentCount())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt())
                .liked(review.isLiked())
                .disliked(review.isDisliked()).build();
    }
    default List<ReviewDto.ResponseInFestival> reviewToResponseInFestival(List<ReviewAddLikeInfo> reviews) {
        return reviews.stream()
                .map(review -> reviewToResponseListElementInFestival(review))
                .collect(Collectors.toList());
    }

    default ReviewDto.ResponseInMyPage reviewToResponseListElementInMyPage(Review review) {
        Festival festival = review.getFestival();
        List<String> pictures = new ArrayList<>();

        for (String[] picture : review.getPictures()) {
            pictures.add(picture[0]);
        }

        return ReviewDto.ResponseInMyPage.builder()
                .reviewId(review.getReviewId())
                .memberId(review.getMember().getMemberId())
                .festivalId(festival.getFestivalId())
                .festivalTitle(festival.getTitle())
                .content(review.getContent())
                .rating(review.getRate())
                .pictures(pictures)
                .likedCount(review.getLikedCount())
                .dislikedCount(review.getDislikedCount())
                .commentCount(review.getCommentCount())
                .createdAt(review.getCreatedAt())
                .modifiedAt(review.getModifiedAt()).build();
    }

    default List<ReviewDto.ResponseInMyPage> reviewToReponseInMyPage(List<Review> reviews) {
        return reviews.stream()
                .map(review -> reviewToResponseListElementInMyPage(review))
                .collect(Collectors.toList());
    }
}
