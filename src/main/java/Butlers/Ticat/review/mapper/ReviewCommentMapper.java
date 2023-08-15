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
public interface ReviewCommentMapper {
    default ReviewComment postToReviewComment(ReviewCommentDto.PostPatch requestBody, long memberId, long reviewId) {
        Member member = new Member();
        member.setMemberId(memberId);

        Review review = new Review();
        review.setReviewId(reviewId);

        ReviewComment reviewComment = new ReviewComment();
        reviewComment.setContent(requestBody.getContent());
        reviewComment.setMember(member);
        reviewComment.setReview(review);

        return reviewComment;
    }

    default ReviewComment patchToReviewComment(ReviewCommentDto.PostPatch requestBody, long memberId, long reviewCommentId) {
        Member member = new Member();
        member.setMemberId(memberId);

        ReviewComment reviewComment = new ReviewComment();
        reviewComment.setMember(member);
        reviewComment.setReviewCommentId(reviewCommentId);
        reviewComment.setContent(requestBody.getContent());

        return reviewComment;
    }

    default List<ReviewCommentDto.Response> reviewsCommentToResponses(List<ReviewComment> reviewComments) {
        return reviewComments.stream()
                .map(reviewComment -> reviewCommentToResponse(reviewComment))
                .collect(Collectors.toList());
    }
    default ReviewCommentDto.Response reviewCommentToResponse(ReviewComment reviewComment) {
        Member member = reviewComment.getMember();
        Review review = reviewComment.getReview();
        Festival festival = review.getFestival();

        return ReviewCommentDto.Response.builder()
                .reviewCommentId(reviewComment.getReviewCommentId())
                .festivalId(festival.getFestivalId())
                .reviewId(review.getReviewId())
                .memberId(member.getMemberId())
                .displayName(member.getDisplayName())
                .profileUrl(member.getProfileUrl())
                .content(reviewComment.getContent())
                .createdAt(reviewComment.getCreatedAt())
                .modifiedAt(reviewComment.getModifiedAt()).build();
    }

    default ReviewCommentDto.ResponseInMyPage reviewCommentToListElement(ReviewComment reviewComment) {
        Review review = reviewComment.getReview();
        Festival festival = review.getFestival();

        return ReviewCommentDto.ResponseInMyPage.builder()
                .reviewCommentId(reviewComment.getReviewCommentId())
                .memberId(reviewComment.getMember().getMemberId())
                .festivalId(festival.getFestivalId())
                .reviewId(review.getReviewId())
                .festivalTitle(festival.getTitle())
                .content(reviewComment.getContent())
                .createdAt(reviewComment.getCreatedAt())
                .modifiedAt(reviewComment.getModifiedAt()).build();
    }

    default List<ReviewCommentDto.ResponseInMyPage> reviewCommentToResponseInMyPage(List<ReviewComment> reviewComments) {
        return reviewComments.stream()
                .map(reviewComment -> reviewCommentToListElement(reviewComment))
                .collect(Collectors.toList());
    }
}
