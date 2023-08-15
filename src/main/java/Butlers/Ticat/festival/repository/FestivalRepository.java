package Butlers.Ticat.festival.repository;

import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Festival;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FestivalRepository extends JpaRepository<Festival,Long> {

    // 두 지점 사이의 거리를 구하는 데 사용하는 함수 ST_DISTANCE_SPHERE
    @Query("SELECT f FROM Festival f WHERE ST_DISTANCE_SPHERE(POINT(f.mapx, f.mapy), POINT(:longitude, :latitude)) <= :distance")
    List<Festival> findFestivalsWithinDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distance);

    @Query("SELECT f FROM Festival f WHERE ST_DISTANCE_SPHERE(POINT(f.mapx, f.mapy), POINT(:longitude, :latitude)) <= :distance")
    Page<Festival> findFestivalsWithinDistance(@Param("latitude") double latitude, @Param("longitude") double longitude, @Param("distance") double distance,Pageable pageable);

    // In 키워드를 사용하면 단일 값이 아닌 다중 값에 대한 조건을 지정할 수 있다
    Page<Festival> findByAreaIn(List<String> areas, Pageable pageable);
    Page<Festival> findByDetailFestivalCategoryIn(List<String> categories,Pageable pageable);
    Page<Festival> findByDetailFestivalCategory(String category,Pageable pageable);
    Page<Festival> findByDetailFestivalCategoryAndAreaIn(String category,List<String> areas, Pageable pageable);
    List<Festival> findByDetailFestivalStatus(DetailFestival.Status status, Sort sort);
    List<Festival> findByDetailFestivalStatus(DetailFestival.Status status);
    List<Festival> findByDetailFestivalCategoryAndDetailFestivalStatus(String category,DetailFestival.Status status);
    List<Festival> findByDetailFestivalCategoryInAndDetailFestivalStatus(List<String> categories,DetailFestival.Status status);
    Page<Festival> findByTitleContainingIgnoreCase(String title, PageRequest of);
    @Query("SELECT f FROM Festival f WHERE lower(f.title) LIKE %:keyword% OR lower(f.area) LIKE %:keyword%")
    Page<Festival> findByTitleOrAreaContainingIgnoreCase(String keyword, PageRequest of);
    @Query("SELECT f FROM Festival f WHERE (LOWER(f.title) LIKE %:keyword% OR LOWER(f.area) LIKE %:keyword%) AND f.detailFestival.category IN :categories")
    Page<Festival> findByKeywordAndCategoryIn(@Param("keyword") String keyword, @Param("categories") List<String> categories, Pageable pageable);

    @Query("SELECT f FROM Festival f " +
            "WHERE ST_DISTANCE_SPHERE(POINT(f.mapx, f.mapy), POINT(:longitude, :latitude)) <= :distance " +
            "AND f.detailFestival.category IN :categories")
    Page<Festival> findFestivalsWithinDistanceAndCategoryIn(
            @Param("latitude") double latitude,
            @Param("longitude") double longitude,
            @Param("distance") double distance,
            @Param("categories") List<String> categories,
            Pageable pageable);

    @Modifying
    @Query(value = "INSERT INTO favorite (festival_id, member_id, ischecked) " +
            "SELECT :festivalId, :memberId, true " +
            "WHERE NOT EXISTS (SELECT festival_id, member_id " +
            "FROM favorite " +
            "WHERE festival_id = :festivalId and member_id = :memberId)", nativeQuery = true)
    int upFavorite(long festivalId, long memberId);

    @Modifying
    @Query(value = "DELETE FROM favorite WHERE festival_id = :festivalId and member_id = :memberId", nativeQuery = true)
    int downFavorite(long festivalId, long memberId);

    Optional<Festival> findByFestivalId(Long festivalId);

}
