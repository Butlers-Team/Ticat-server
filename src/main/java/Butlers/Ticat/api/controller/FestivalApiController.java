package Butlers.Ticat.api.controller;

import Butlers.Ticat.api.service.FestivalApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/festival")
@RequiredArgsConstructor
public class FestivalApiController {

    private final FestivalApiService festivalApiService;

    @PostMapping("/list")
    public ResponseEntity<String> resetFestivalList() throws IOException {
        festivalApiService.getFestivalList();

        return ResponseEntity.ok("행사리스트 불러오기");
    }

    @PostMapping("/detail")
    public ResponseEntity<String> detailFestivalList() throws IOException {
        festivalApiService.getFestivalDetail();

        return ResponseEntity.ok("행사상세1 불러오기");
    }

    @PostMapping("/overview")
    public ResponseEntity<String> overviewFestivalList() throws IOException {
        festivalApiService.getFestivalOverView();

        return ResponseEntity.ok("행사상세2 불러오기");
    }

}
