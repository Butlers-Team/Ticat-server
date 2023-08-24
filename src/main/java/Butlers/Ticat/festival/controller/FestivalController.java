package Butlers.Ticat.festival.controller;

import Butlers.Ticat.dto.MultiResponseDto;
import Butlers.Ticat.dto.SingleResponseDto;
import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.mapper.FestivalMapper;
import Butlers.Ticat.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/festivals")
@RequiredArgsConstructor
@Validated
public class FestivalController {
    private final FestivalService festivalService;
    private final FestivalMapper mapper;

    // 축제 상세 페이지
    @GetMapping("/{festival-id}")
    public ResponseEntity getFestival(@Positive @PathVariable("festival-id") long festivalId){
        Festival festival = festivalService.findFestival(festivalId);
        FestivalDto.Response ResponseFestival = festivalService.isFestivalLiked(mapper.festivalToResponse(festival));

        return new ResponseEntity<>(ResponseFestival,HttpStatus.OK);
    }

    @GetMapping("/distance")
    public ResponseEntity getFestivalsWithinDistance(@Positive @RequestParam double mapX,
                                               @Positive @RequestParam double mapY,
                                               @Positive @RequestParam double distance){
        List<Festival> festivals = festivalService.findFestivalsWithinDistance(mapX,mapY,distance);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.festivalsToFestivalListResponses(festivals)),HttpStatus.OK);
    }

    // 축제 메인페이지 배너
    @GetMapping("/banner")
    public ResponseEntity getFestivalsByStatus() {
        List<Festival> festivals = festivalService.findFestivalByStatus();
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.festivalsToFestivalListResponses(festivals)),HttpStatus.OK);
    }

    // 축제 리스트 불러오기
    @GetMapping("/list")
    public ResponseEntity getFilteredFestivals(@RequestParam(required = false) String category,
                                               @RequestParam(required = false) List<String> areas,
                                               @Positive @RequestParam int page,
                                               @Positive @RequestParam int size) {
        Page<Festival> pageFestivals = festivalService.getFilteredFestivalList(category, areas, page, size);
        List<Festival> festivals = pageFestivals.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.festivalsToFestivalListResponses(festivals), pageFestivals), HttpStatus.OK);
    }

    // 축제 제목으로 검색
    @GetMapping("/calendar")
    public ResponseEntity getFilterFestivalToCalendar(@RequestParam(required = false) String title,
                                                      @Positive @RequestParam int page,
                                                      @Positive @RequestParam int size) {
        Page<Festival> pageFestivals = festivalService.findFestivalsByTitle(title, page, size);
        List<Festival> festivals = pageFestivals.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(
                mapper.festivalsToFestivalListResponses(festivals)
                , pageFestivals), HttpStatus.OK);
    }

    // 지도페이지
    @GetMapping("/map")
    public ResponseEntity getFilteredFestivalsByMap(@Positive @RequestParam(defaultValue = "126.9816417") double longitude,
                                                    @Positive @RequestParam(defaultValue = "37.57037778") double latitude,
                                                    @RequestParam(required = false) List<String> categories,
                                                    @RequestParam(required = false) String sortBy,
                                                    @RequestParam(required = false, defaultValue = "ONGOING") List<DetailFestival.Status> status,
                                                    @RequestParam(required = false) String keyword,
                                                    @Positive @RequestParam int page,
                                                    @Positive @RequestParam int size) {
        Page<Festival> pageFestivals;

        if (keyword != null & keyword.equals("")) {
            pageFestivals = festivalService.findByKeywordAndAreas(keyword, categories, sortBy, status, page, size);
        } else {
            pageFestivals = festivalService.getFilteredFestivals(longitude, latitude, categories, sortBy, status, page, size);
        }

        List<Festival> festivals = pageFestivals.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.festivalsToFestivalListResponses(festivals), pageFestivals), HttpStatus.OK);
    }

    // 좋아요 하기
    @PostMapping("/{festival-id}/favorite")
    public ResponseEntity<String> postFavorite(@PathVariable("festival-id") @Positive long festivalId) {
        festivalService.createFavorite(festivalId);

        return ResponseEntity.ok("좋아요");
    }

    //좋아요 취소
    @DeleteMapping("/{festival-id}/unfavorite")
    public ResponseEntity<String> deleteFavorite(@PathVariable("festival-id") @Positive long festivalId) {
        festivalService.cancleFavorite(festivalId);

        return ResponseEntity.ok("좋아요 취소");
    }

    //상세페이지 추천축제
    @GetMapping("/detailrecommend")
    public ResponseEntity getDetailRecommend(@RequestParam String category){
        List<Festival> festivals = festivalService.findDetailRecommend(category);

        return new ResponseEntity<>(mapper.festivalsToFestivalListResponses(festivals),HttpStatus.OK);
    }

    // 메인화면 관심사로 축제 추천
    @GetMapping("/mainrecommend")
    public ResponseEntity getMainRecommend(){
        List<Festival> festivals = festivalService.findMainRecommend();

        return new ResponseEntity<>(mapper.festivalsToFestivalListResponses(festivals),HttpStatus.OK);
    }

    // 두 좌표 사이 거리 구하기
    @GetMapping("/km")
    public ResponseEntity getKm(@RequestParam @Positive double lat1,@RequestParam @Positive double lon1 , @RequestParam @Positive double lat2,@RequestParam @Positive double lon2){
        FestivalDto.DistanceResponse distanceResponse = festivalService.calculateDistance(lat1, lon1, lat2, lon2);

        return new ResponseEntity<>(distanceResponse,HttpStatus.OK);
    }
}

