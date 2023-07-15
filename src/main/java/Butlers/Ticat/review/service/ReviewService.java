package Butlers.Ticat.review.service;

import Butlers.Ticat.aws.service.AwsS3Service;
import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.service.FestivalService;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.service.MemberService;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static long NUMBER_OF_NON_LOGIN = -1;

    private final ReviewRepository reviewRepository;
    private final MemberService memberService;
    private final FestivalService festivalService;
    private final AwsS3Service awsS3Service;

    // 리뷰 등록
    public void registerReview(Review review, List<MultipartFile> files) {
        long memberId = review.getMember().getMemberId();

        // 로그인 여부 확인
        if (memberId == NUMBER_OF_NON_LOGIN) {
            throw new BusinessLogicException(ExceptionCode.AVAILABLE_AFTER_LOGIN);
        }

        Member member = memberService.findVerifiedMember(memberId);
        Festival festival = festivalService.findFestival(review.getFestival().getFestivalId());
        review.setMember(member);
        review.setFestival(festival);

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

        reviewRepository.save(review);
    }

    // 축제 상세 페이지에 속한 리뷰 리스트 불러오기
    public Page<Review> getReviewListInFestivalDetail(long festivalId, int page, int size) {
        return reviewRepository.findAllByFestivalFestivalId(festivalId, PageRequest.of(page - 1, size, Sort.by("reviewId").descending()));
    }
}
