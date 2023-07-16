package Butlers.Ticat.review.mapper;

import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.review.dto.ReviewCommentDto;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.review.entity.ReviewComment;
import org.mapstruct.Mapper;

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
}
