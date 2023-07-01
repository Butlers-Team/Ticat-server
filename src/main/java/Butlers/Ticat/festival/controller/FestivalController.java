package Butlers.Ticat.festival.controller;

import Butlers.Ticat.dto.MultiResponseDto;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.mapper.FestivalMapper;
import Butlers.Ticat.festival.service.FestivalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/festivals")
@RequiredArgsConstructor
public class FestivalController {

    private final FestivalService festivalService;

    private final FestivalMapper mapper;

    @GetMapping
    public ResponseEntity getFestivals(@Positive @RequestParam int page,
                                       @Positive @RequestParam int size){
        Page<Festival> pageFestivals = festivalService.findFestivals(page, size);
        List<Festival> festivals = pageFestivals.getContent();

        return new ResponseEntity<>(new MultiResponseDto<>(mapper.festivalsToFestivalListResponses(festivals),pageFestivals), HttpStatus.OK);
    }
}
