package Butlers.Ticat.festival.repository;

import Butlers.Ticat.festival.entity.Festival;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival,Long> {

    Optional<Festival> findByContentId(String contentId);

    // 두 지점 사이의 거리를 구하는 데 사용하는 함수 ST_DISTANCE_SPHERE
    @Query("SELECT f FROM Festival f WHERE ST_DISTANCE_SPHERE(POINT(f.mapx, f.mapy), POINT(:longitude, :latitude)) <= :distance")
    List<Festival> findFestivalsWithinDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distance);
}
