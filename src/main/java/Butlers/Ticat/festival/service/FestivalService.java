package Butlers.Ticat.festival.service;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    // 전체 리스트
    public Page<Festival> findFestivals(int page, int size){
        return festivalRepository.findAll(PageRequest.of(page-1,size));
    }

    // 위도 경도 거리에 따른 축제 찾기
    public List<Festival> findFestivalsWithinDistance(double latitude, double longitude, double distance) {
        return festivalRepository.findFestivalsWithinDistance(longitude,latitude, distance);
    }

    // 축제 상세 페이지
    public Festival findFestival(long contentId) {
        Optional<Festival> optionalFestival = festivalRepository.findByContentId(contentId);
        Festival festival = optionalFestival.orElseThrow();
        return festival;
    }

    // 지역에 따라 축제 찾기
    public Page<Festival> findFestivalByArea(List<String> areas,int page,int size) {
        return festivalRepository.findByAreaIn(areas,PageRequest.of(page-1,size));
    }

    // 메인 배너페이지
    public List<Festival> findFestivalByStatus(DetailFestival.Status status) {

        List<Festival> festivals = festivalRepository.findByDetailFestivalStatus(status);

        if (festivals.size() >= 4) {
            Collections.shuffle(festivals);
            festivals = festivals.subList(0, 4);
        }

        return festivals;
    }

    // 카테고리와 지역 이용해서 축제 찾기
    public Page<Festival> findByCategoryAndArea(String category,List<String> areas,int page,int size) {
        return festivalRepository.findByDetailFestivalCategoryAndAreaIn(category,areas,PageRequest.of(page-1,size));
    }

    // 카테고리 이용해서 축제 찾기
    public Page<Festival> findByCategory(String category,int page,int size) {
        return festivalRepository.findByDetailFestivalCategory(category,PageRequest.of(page-1,size));
    }

}
