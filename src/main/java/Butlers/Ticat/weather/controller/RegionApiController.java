package Butlers.Ticat.weather.controller;

import Butlers.Ticat.weather.service.RegionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/region")
@RequiredArgsConstructor
public class RegionApiController {
    private final RegionService regionService;

    @PostMapping
    public ResponseEntity<String> resetRegionList() {

        regionService.resetRegionList();

        return ResponseEntity.ok("초기화 성공");
    }

}
