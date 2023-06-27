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
    public ResponseEntity<String> resetTourList() throws IOException {
        festivalApiService.getFestivalList();

        return ResponseEntity.ok("행사리스트 불러오기");
    }

}
