package Butlers.Ticat.festival.controller;

import Butlers.Ticat.dto.MultiResponseDto;
import Butlers.Ticat.dto.SingleResponseDto;
import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.mapper.FestivalMapper;
import Butlers.Ticat.festival.service.FestivalApiService;
import Butlers.Ticat.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.io.IOException;
import java.util.List;

import static Butlers.Ticat.festival.entity.DetailFestival.Status.ONGOING;

@RestController
@RequestMapping("/festivals")
@RequiredArgsConstructor
@Validated
public class FestivalController {

    private final FestivalService festivalService;
    private final FestivalMapper mapper;

//    @GetMapping
//    public ResponseEntity getFestivals(@Positive @RequestParam int page,
//                                       @Positive @RequestParam int size){
//        Page<Festival> pageFestivals = festivalService.findFestivals(page, size);
//        List<Festival> festivals = pageFestivals.getContent();
//
//        return new ResponseEntity<>(new MultiResponseDto<>(mapper.festivalsToFestivalListResponses(festivals),pageFestivals), HttpStatus.OK);
//    }

    @GetMapping("/{contentId}")
    public ResponseEntity getFestival(@Positive @PathVariable("contentId") long contentId){
        Festival festival = festivalService.findFestival(contentId);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.festivalToResponse(festival)),HttpStatus.OK);
    }

    @GetMapping("/distance")
    public ResponseEntity getFestivalsWithinDistance(@Positive @RequestParam double mapX,
                                               @Positive @RequestParam double mapY,
                                               @Positive @RequestParam double distance){
        List<Festival> festivals = festivalService.findFestivalsWithinDistance(mapX,mapY,distance);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.festivalsToFestivalListResponses(festivals)),HttpStatus.OK);
    }

//    @GetMapping("/area")
//    public ResponseEntity getFestivalsByDistricts(@RequestParam List<String> areas) {
//        List<Festival> festivals = festivalService.findFestivalByArea(areas);
//        return new ResponseEntity<>(new SingleResponseDto<>(mapper.festivalsToFestivalListResponses(festivals)),HttpStatus.OK);
//    }

    @GetMapping("/banner")
    public ResponseEntity getFestivalsByStatus() {
        List<Festival> festivals = festivalService.findFestivalByStatus(ONGOING);
        return new ResponseEntity<>(new SingleResponseDto<>(mapper.festivalsToFestivalListResponses(festivals)),HttpStatus.OK);
    }

    // 축제 리스트 불러오기
    @GetMapping("/list")
    public ResponseEntity getFilteredFestivals(@RequestParam(required = false) String category,
                                               @RequestParam(required = false) List<String> areas,
                                               @Positive @RequestParam int page,
                                               @Positive @RequestParam int size) {
        if (category == null || category.equalsIgnoreCase("전체")) {
            if (areas != null && !areas.isEmpty()) {
                Page<Festival> pageFestivals = festivalService.findFestivalByArea(areas,page,size);
                List<Festival> festivals = pageFestivals.getContent();
                return new ResponseEntity<>(new MultiResponseDto<>(mapper.festivalsToFestivalListResponses(festivals),pageFestivals), HttpStatus.OK);
            } else {
                Page<Festival> pageFestivals = festivalService.findFestivals(page, size);
                List<Festival> festivals = pageFestivals.getContent();
                return new ResponseEntity<>(new MultiResponseDto<>(mapper.festivalsToFestivalListResponses(festivals),pageFestivals), HttpStatus.OK);
            }
        } else {
            if (areas != null && !areas.isEmpty()) {
                Page<Festival> pageFestivals = festivalService.findByCategoryAndArea(category, areas, page, size);
                List<Festival> festivals = pageFestivals.getContent();
                return new ResponseEntity<>(new MultiResponseDto<>(mapper.festivalsToFestivalListResponses(festivals),pageFestivals), HttpStatus.OK);
            } else {

                Page<Festival> pageFestivals = festivalService.findByCategory(category,page, size);
                List<Festival> festivals = pageFestivals.getContent();
                return new ResponseEntity<>(new MultiResponseDto<>(mapper.festivalsToFestivalListResponses(festivals),pageFestivals), HttpStatus.OK);
            }
        }
    }
}
