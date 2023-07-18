package Butlers.Ticat.weather.repository;

import Butlers.Ticat.weather.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionRepository extends JpaRepository<Region,Long> {
}
