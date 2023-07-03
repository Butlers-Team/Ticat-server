package Butlers.Ticat.festival.service;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public Page<Festival> findFestivals(int page, int size){
        return festivalRepository.findAll(PageRequest.of(page-1,size));
    }

    public List<Festival> findFestivalsWithinDistance(double latitude, double longitude, double distance) {
        return festivalRepository.findFestivalsWithinDistance(longitude,latitude, distance);
    }

    public Festival findFestival(long contentId) {
        Optional<Festival> optionalFestival = festivalRepository.findByContentId(contentId);
        Festival festival = optionalFestival.orElseThrow();
        return festival;
    }

    public List<Festival> findFestivalByArea(List<String> areas) {
        return festivalRepository.findByAreaIn(areas);
    }


}
