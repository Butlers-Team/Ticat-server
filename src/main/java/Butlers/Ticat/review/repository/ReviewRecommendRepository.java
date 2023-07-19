package Butlers.Ticat.review.repository;

import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.review.entity.Review;
import Butlers.Ticat.review.entity.ReviewRecommend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRecommendRepository extends JpaRepository<ReviewRecommend, Long> {
    Optional<ReviewRecommend> findByMemberAndReview(Member member, Review review);
}
