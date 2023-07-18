package Butlers.Ticat.review.repository;

import Butlers.Ticat.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByFestivalFestivalId(Long festivalId, Pageable pageable);
}
