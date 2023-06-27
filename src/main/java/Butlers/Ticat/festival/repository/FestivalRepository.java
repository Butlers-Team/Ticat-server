package Butlers.Ticat.festival.repository;

import Butlers.Ticat.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival,Long> {

    Optional<Festival> findByContentId(String contentId);

}
