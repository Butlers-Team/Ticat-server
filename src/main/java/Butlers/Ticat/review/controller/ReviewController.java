package Butlers.Ticat.review.controller;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.dto.MultiResponseDto;
import Butlers.Ticat.review.dto.ReviewDto;
import Butlers.Ticat.review.entity.Review;
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

        reviewService.updateReview(reviewMapper.patchToReveiw(review, authenticationMemberId, reviewId), reviewImage);

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

    @DeleteMapping("/review/{review-id}")
    public ResponseEntity deleteReview(@PathVariable("review-id") long reviewId) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();

        reviewService.deleteReview(authenticationMemberId, reviewId);

        return new ResponseEntity<>("리뷰 삭제가 완료되었습니다.", HttpStatus.NO_CONTENT);
    }
}
