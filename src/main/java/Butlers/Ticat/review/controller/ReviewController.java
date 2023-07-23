package Butlers.Ticat.review.controller;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.dto.MultiResponseDto;
import Butlers.Ticat.review.dto.ReviewCommentDto;
import Butlers.Ticat.review.dto.ReviewDto;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.review.entity.ReviewComment;
import Butlers.Ticat.review.entity.ReviewPlus;
import Butlers.Ticat.review.entity.ReviewRecommend;
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

    // 리뷰 수정 전 권한 확인 요청
    @GetMapping("/reviews/{review-id}")
    public ResponseEntity beforePatchRequestCheckReviewModificationPermission(@PathVariable("review-id") long reviewId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.checkReviewModificationPermission(authenticationMemberId, reviewId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 축제 상세페이지 내부 리뷰 리스트 불러오기
    @GetMapping("/festivals/{festival-id}/reviews")
    public ResponseEntity getReviewsInFestivalDetail(@PathVariable("festival-id") long festivalId,
                                    @Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        Page<Review> pageReviews = reviewService.getReviewListInFestivalDetail(festivalId, page, size);
        List<Review> reviews = pageReviews.getContent();
        List<ReviewPlus> reviewPluses = reviewService.reviewToPlus(reviews, authenticationMemberId);

        return new ResponseEntity<>(new MultiResponseDto<>(reviewMapper.reviewToResponseInFestival(reviewPluses), pageReviews), HttpStatus.OK);
    }

    // 리뷰에 속한 댓글 리스트 불러오기
    @GetMapping("/reviews/{review-id}/comments")
    public ResponseEntity getReviewComments(@PathVariable("review-id") long reviewId,
                                            @Positive @RequestParam int page,
                                            @Positive @RequestParam int size) {
        Page<ReviewComment> pageReviewComments = reviewService.getReviewCommentListInReview(reviewId, page, size);
        List<ReviewComment> comments = pageReviewComments.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(reviewCommentMapper.reviewsCommentToResponses(comments), pageReviewComments), HttpStatus.OK);
    }

    @DeleteMapping("/reviews/{review-id}")
    public ResponseEntity deleteReview(@PathVariable("review-id") long reviewId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.deleteReview(authenticationMemberId, reviewId);

        return new ResponseEntity<>("리뷰 삭제가 완료되었습니다.", HttpStatus.NO_CONTENT);
    }

    @PostMapping("/reviews/{review-id}/liked")
    public ResponseEntity postRecommendReview(@PathVariable("review-id") long reviewId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.recommendReview(authenticationMemberId, reviewId);

        return new ResponseEntity<>("해당 리뷰를 추천했습니다.", HttpStatus.OK);
    }

    @PostMapping("reviews/{review-id}/disliked")
    public ResponseEntity postUnrecommendReview(@PathVariable("review-id") long reviewId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.unrecommendReview(authenticationMemberId, reviewId);

        return new ResponseEntity<>("해당 리뷰를 비추천했습니다.", HttpStatus.OK);
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

    // 리뷰 댓글 수정 전 권한 확인
    @GetMapping("/comments/{comment-id}")
    public ResponseEntity beforePatchRequestCheckReviewCommentModificationPermission(@PathVariable("comment-id") long reviewCommentId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.checkReviewCommentModificationPermission(authenticationMemberId, reviewCommentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/comments/{comment-id}")
    public ResponseEntity deleteComment(@PathVariable("comment-id") long commentId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.deleteReviewComment(authenticationMemberId, commentId);

        return new ResponseEntity<>("리뷰 댓글 삭제가 완료되었습니다.", HttpStatus.OK);
    }
}
