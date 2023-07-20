package Butlers.Ticat.review.service;

import Butlers.Ticat.aws.service.AwsS3Service;
import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.service.FestivalService;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.service.MemberService;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.review.entity.ReviewComment;
import Butlers.Ticat.review.entity.ReviewRecommend;
import Butlers.Ticat.review.repository.ReviewCommentRepository;
import Butlers.Ticat.review.repository.ReviewRecommendRepository;
import Butlers.Ticat.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private static long NUMBER_OF_NON_LOGIN = -1;

    private final ReviewRepository reviewRepository;
    private final ReviewCommentRepository reviewCommentRepository;
    private final ReviewRecommendRepository reviewRecommendRepository;
    private final MemberService memberService;
    private final FestivalService festivalService;
    private final AwsS3Service awsS3Service;

    // 리뷰 등록
    public void registerReview(Review review, List<MultipartFile> files) {
        long memberId = review.getMember().getMemberId();

        checkLogin(memberId);

        Member member = memberService.findVerifiedMember(memberId);
        Festival festival = festivalService.findFestival(review.getFestival().getFestivalId());
        review.setMember(member);
        review.setFestival(festival);
        reviewRateCreate(review, festival);

        uploadPicture(review, files);

        reviewRepository.save(review);
    }

    // 리뷰 생성시 리뷰 평점,카운트 수정
    private void reviewRateCreate(Review review, Festival festival) {
        int newReviewCount = festival.getReviews().size() + 1;
        double newReviewRating = (festival.getReviewRating() * festival.getReviews().size() + review.getRate()) / newReviewCount;

        festival.setReviewCount(newReviewCount);
        festival.setReviewRating(newReviewRating);
    }

    // 리뷰 수정
    public void updateReview(Review review, List<MultipartFile> files) {
        long memberId = review.getMember().getMemberId();

        checkLogin(memberId);

        Review findedReview = findVerifiedReview(review.getReviewId());

        checkAuthor(memberId, findedReview.getMember().getMemberId());

        findedReview.setContent(review.getContent());
        reviewRateUpdate(review, findedReview);

        deletePicture(review);

        uploadPicture(findedReview, files);

        reviewRepository.save(findedReview);
    }

    // 리뷰 수정시 리뷰 평점,카운트 수정
    private void reviewRateUpdate(Review review, Review findedReview) {
        Festival festival = festivalService.findFestival(findedReview.getFestival().getFestivalId());
        double oldRating = findedReview.getRate();
        findedReview.setRate(review.getRate());
        double newRating = review.getRate();
        int reviewCount = festival.getReviewCount();
        double reviewRating = festival.getReviewRating();
        reviewRating = (reviewRating * reviewCount - oldRating + newRating) / reviewCount;
        festival.setReviewRating(reviewRating);
    }

    // 리뷰 수정 요청 전 권한 확인 메서드
    public void checkReviewModificationPermission(long memberId, long reviewId) {
        checkLogin(memberId);
        checkAuthor(memberId, findVerifiedReview(reviewId).getMember().getMemberId());
    }

    // 기존에 업로드 된 사진 삭제
    private void deletePicture(Review review) {
        for (String[] picture : review.getPictures()) {
            awsS3Service.deleteFile(picture[1]);
        }
    }

    // 작성자 확인
    private static void checkAuthor(long memberId, long authorId) {
        if (authorId != memberId) {
            throw new BusinessLogicException(ExceptionCode.ONLY_AUTHOR);
        }
    }

    // 로그인 확인
    private static void checkLogin(long memberId) {
        if (memberId == NUMBER_OF_NON_LOGIN) {
            throw new BusinessLogicException(ExceptionCode.AVAILABLE_AFTER_LOGIN);
        }
    }

    // 사진 업로드
    private void uploadPicture(Review review, List<MultipartFile> files) {
        // 사진이 첨부되어 있을경우 처리
        if (files != null) {
            if (files.size() > 4) {
                throw new BusinessLogicException(ExceptionCode.CANT_UPLOAD_MORE_THAN_FOUR);
            }
            List<String[]> pictures = new ArrayList<>();
            for (MultipartFile file : files) {
                String[] uriList = awsS3Service.uploadFile(file);
                pictures.add(uriList);
            }
            review.setPictures(pictures);
        }
    }

    // 리뷰 찾기
    private Review findVerifiedReview(long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);

        return optionalReview.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.REVIEW_NOT_FOUND));
    }

    // 축제 상세 페이지에 속한 리뷰 리스트 불러오기
    public Page<Review> getReviewListInFestivalDetail(long festivalId, int page, int size) {
        return reviewRepository.findAllByFestivalFestivalId(festivalId, PageRequest.of(page - 1, size, Sort.by("reviewId").descending()));
    }

    // 리뷰에 속한 리뷰 댓글 리스트 불러오기
    public Page<ReviewComment> getReviewCommentListInReview(long reviewId, int page, int size) {
        return reviewCommentRepository.findAllByReviewReviewId(reviewId, PageRequest.of(page - 1, size, Sort.by("reviewCommentId").descending()));
    }

    // 리뷰 삭제
    public void deleteReview(long memberId, long reviewId) {
        checkLogin(memberId);

        Review review = findVerifiedReview(reviewId);

        checkAuthor(memberId, review.getMember().getMemberId());
        reviewRateDelete(review);

        deletePicture(review);
        reviewRepository.delete(review);
    }

    // 리뷰 삭제시 축제 평점,카운트 수정
    private void reviewRateDelete(Review review) {
        Festival festival = festivalService.findFestival(review.getFestival().getFestivalId());
        int currentReviewCount = festival.getReviews().size();
        double currentReviewRating = festival.getReviewRating();

        if (currentReviewCount > 1) {
            int newReviewCount = currentReviewCount - 1;
            double totalReviewRating = currentReviewRating * currentReviewCount;
            double deletedReviewRate = review.getRate();
            double newReviewRating = (totalReviewRating - deletedReviewRate) / newReviewCount;

            festival.setReviewCount(newReviewCount);
            festival.setReviewRating(newReviewRating);
        } else {
            festival.setReviewCount(0);
            festival.setReviewRating(0.0);
        }
    }

    // 리뷰 추천 여부 확인
    public String checkLike(long memberId, long reviewId) {
        if (memberId == NUMBER_OF_NON_LOGIN) {
            return "";
        } else {
            Member member = memberService.findVerifiedMember(memberId);
            Review review = findVerifiedReview(reviewId);

            return findReviewRecommend(member, review).getRecommendStatus().getStatus();
        }
    }

    // 리뷰 추천
    public ReviewRecommend recommendReview(long memberId, long reviewId) {
        checkLogin(memberId);
        Member member = memberService.findVerifiedMember(memberId);
        Review review = findVerifiedReview(reviewId);

        ReviewRecommend reviewRecommend = findReviewRecommend(member, review);

        if (reviewRecommend.getRecommendStatus() == ReviewRecommend.RecommendStatus.NON) {
            reviewRecommend.setRecommendStatus(ReviewRecommend.RecommendStatus.RECOMMEND);
            review.setLiked(review.getLiked() + 1);
        } else {
            reviewRecommend.setRecommendStatus(ReviewRecommend.RecommendStatus.NON);
            review.setLiked(review.getLiked() - 1);
        }

        return reviewRecommendRepository.save(reviewRecommend);
    }

    // 리뷰 비추천
    public ReviewRecommend unrecommendReivew(long memberId, long reviewId) {
        checkLogin(memberId);
        Member member = memberService.findVerifiedMember(memberId);
        Review review = findVerifiedReview(reviewId);

        ReviewRecommend reviewRecommend = findReviewRecommend(member, review);

        if (reviewRecommend.getRecommendStatus() == ReviewRecommend.RecommendStatus.NON) {
            reviewRecommend.setRecommendStatus(ReviewRecommend.RecommendStatus.UNRECOMMENDED);
            review.setDisliked(review.getDisliked() + 1);
        } else {
            reviewRecommend.setRecommendStatus(ReviewRecommend.RecommendStatus.NON);
            review.setDisliked(review.getDisliked() - 1);
        }

        return reviewRecommendRepository.save(reviewRecommend);
    }

    // 리뷰 추천 객체 찾기 (없을 경우 새로운 객체를 반환)
    private ReviewRecommend findReviewRecommend(Member member, Review review) {
        Optional<ReviewRecommend> optionalReviewRecommend = reviewRecommendRepository.findByMemberAndReview(member, review);

        if (optionalReviewRecommend.isPresent()) {
            return optionalReviewRecommend.get();
        } else {
            ReviewRecommend reviewRecommend = new ReviewRecommend();
            reviewRecommend.setMember(member);
            reviewRecommend.setReview(review);

            return reviewRecommend;
        }
    }

    // 리뷰 댓글 등록
    public void registerReviewComment(ReviewComment reviewComment) {
        long memberId = reviewComment.getMember().getMemberId();

        checkLogin(memberId);

        Member member = memberService.findVerifiedMember(memberId);
        Review review = findVerifiedReview(reviewComment.getReview().getReviewId());

        reviewComment.setMember(member);
        reviewComment.setReview(review);
        increaseReviewCommentCount(review);

        reviewCommentRepository.save(reviewComment);
    }

    // 리뷰 댓글 수정
    public void updateReviewComment(ReviewComment reviewComment) {
        long memberId = reviewComment.getMember().getMemberId();

        checkLogin(memberId);

        ReviewComment findedReviewComment = findVerifiedReviewComment(reviewComment.getReviewCommentId());

        checkAuthor(memberId, findedReviewComment.getMember().getMemberId());

        findedReviewComment.setContent((reviewComment.getContent()));

        reviewCommentRepository.save(findedReviewComment);
    }

    // 리뷰 댓글 수정 요청 전 권한 확인 메서드
    public void checkReviewCommentModificationPermission(long memberId, long reviewCommentId) {
        checkLogin(memberId);
        checkAuthor(memberId, findVerifiedReviewComment(reviewCommentId).getMember().getMemberId());
    }

    // 리뷰 댓글 찾기
    private ReviewComment findVerifiedReviewComment(long reviewCommentId) {
        Optional<ReviewComment> optionalReviewComment = reviewCommentRepository.findById(reviewCommentId);

        return optionalReviewComment.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.REVIEW_COMMENT_NOT_FOUND));
    }

    // 리뷰 댓글 삭제
    public void deleteReviewComment(long memberId, long reviewCommentId) {
        checkLogin(memberId);

        ReviewComment reviewComment = findVerifiedReviewComment(reviewCommentId);

        checkAuthor(memberId, reviewComment.getMember().getMemberId());

        Review review = reviewComment.getReview();
        decreaseReviewCommentCount(review);

        reviewCommentRepository.delete(reviewComment);
    }

    // 리뷰 댓글 등록 시 리뷰의 댓글 수 증가
    private void increaseReviewCommentCount(Review review) {
        review.setCommentCount(review.getCommentCount() + 1);
    }

    // 리뷰 댓글 제거 시 리뷰의 댓글 수 차감
    private void decreaseReviewCommentCount(Review review) {
        review.setCommentCount(review.getCommentCount() - 1);
    }
}
