package Butlers.Ticat.review.mapper;

import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.review.dto.ReviewCommentDto;
import Butlers.Ticat.review.dto.ReviewDto;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.review.entity.ReviewComment;
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

    default ReviewDto.ResponseInFestival reviewToResponseListElementInFestival(Review review) {
        Member member = review.getMember();
        List<String> pictures = new ArrayList<>();

        for (String[] picture : review.getPictures()) {
            pictures.add(picture[0]);
        }

        return ReviewDto.ResponseInFestival.builder()
                .reviewId(review.getReviewId())
                .memberId(member.getMemberId())
                .displayName(member.getDisplayName())
                .content(review.getContent())
                .rating(review.getRate())
                .pictures(pictures)
                .comments(reviewsCommentToResponses(review.getComments())).build();
    }
    default List<ReviewDto.ResponseInFestival> reviewToResponseInFestival(List<Review> reviews) {
        return reviews.stream()
                .map(review -> reviewToResponseListElementInFestival(review))
                .collect(Collectors.toList());
    }

    default List<ReviewCommentDto.Response> reviewsCommentToResponses(List<ReviewComment> reviewComments) {
        return reviewComments.stream()
                .map(reviewComment -> reviewCommentToResponse(reviewComment))
                .collect(Collectors.toList());
    }
    default ReviewCommentDto.Response reviewCommentToResponse(ReviewComment reviewComment) {
        Member member = reviewComment.getMember();

        return ReviewCommentDto.Response.builder()
                .reviewCommentId(reviewComment.getReviewCommentId())
                .memberId(member.getMemberId())
                .displayName(member.getDisplayName())
                .content(reviewComment.getContent()).build();
    }
}
