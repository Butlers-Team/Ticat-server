package Butlers.Ticat.festival.repository;

import Butlers.Ticat.festival.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
     Optional<Favorite> findByFestivalFestivalIdAndMemberMemberId(Long festivalId, Long memberId);
}
