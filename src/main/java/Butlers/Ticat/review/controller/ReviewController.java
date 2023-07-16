package Butlers.Ticat.review.controller;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.dto.MultiResponseDto;
import Butlers.Ticat.review.dto.ReviewCommentDto;
import Butlers.Ticat.review.dto.ReviewDto;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.review.mapper.ReviewCommentMapper;
import Butlers.Ticat.review.mapper.ReviewMapper;
import Butlers.Ticat.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final ReviewCommentMapper reviewCommentMapper;

    @PostMapping(path = "/festivals/{festival-id}/reviews",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity postReview(@PathVariable("festival-id") long festivalId,
                                     @RequestPart ReviewDto.PostPatch review,
                                     @RequestPart(required = false) List<MultipartFile> reviewImage) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.registerReview(reviewMapper.postToReview(review, authenticationMemberId, festivalId), reviewImage);

        return new ResponseEntity<>("리뷰 등록이 완료되었습니다.", HttpStatus.OK);
    }

    @PatchMapping(path = "/reviews/{review-id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity patchReview(@PathVariable("review-id") long reviewId,
                                      @RequestPart ReviewDto.PostPatch review,
                                      @RequestPart(required = false) List<MultipartFile> reviewImage) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.updateReview(reviewMapper.patchToReview(review, authenticationMemberId, reviewId), reviewImage);

        return new ResponseEntity<>("리뷰 수정이 완료되었습니다.", HttpStatus.OK);
    }

    @GetMapping("/festivals/{festival-id}/reviews")
    public ResponseEntity getReviewsInFestivalDetail(@PathVariable("festival-id") long festivalId,
                                    @Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Review> pageReviews = reviewService.getReviewListInFestivalDetail(festivalId, page, size);
        List<Review> reviews = pageReviews.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(reviewMapper.reviewToResponseInFestival(reviews), pageReviews), HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{review-id}")
    public ResponseEntity deleteReview(@PathVariable("review-id") long reviewId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.deleteReview(authenticationMemberId, reviewId);

        return new ResponseEntity<>("리뷰 삭제가 완료되었습니다.", HttpStatus.NO_CONTENT);
    }

    // 리뷰 댓글
    @PostMapping("/reviews/{review-id}/comments")
    public ResponseEntity postComment(@PathVariable("review-id") long reviewId,
                                      @RequestBody ReviewCommentDto.PostPatch comment) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.registerReviewComment(reviewCommentMapper.postToReviewComment(comment, authenticationMemberId, reviewId));

        return new ResponseEntity<>("리뷰 댓글 등록이 완료되었습니다.", HttpStatus.OK);
    }

    @PatchMapping("/comments/{comment-id}")
    public ResponseEntity patchComment(@PathVariable("comment-id") long commentId,
                                       @RequestBody ReviewCommentDto.PostPatch comment) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.updateReviewComment(reviewCommentMapper.patchToReviewComment(comment, authenticationMemberId, commentId));

        return new ResponseEntity<>("리뷰 댓글 수정이 완료되었습니다.", HttpStatus.OK);
    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") long commentId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.deleteReviewComment(authenticationMemberId, commentId);

        return new ResponseEntity<>("리뷰 댓글 삭제가 완료되었습니다.", HttpStatus.NO_CONTENT);
    }
}
