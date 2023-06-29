package Butlers.Ticat.weather.controller;

import Butlers.Ticat.weather.dto.WeatherDto;
import Butlers.Ticat.weather.service.RegionService;
import Butlers.Ticat.weather.service.WeatherApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherApiController {

    private final WeatherApiService weatherApiService;

    private final RegionService regionService;

    @GetMapping
    @Transactional
    public ResponseEntity getRegionWeather(@RequestParam(defaultValue = "60") int currentLatitude,
                                           @RequestParam(defaultValue = "127") int currentLongitude) {
        Long regionId = regionService.getRegionId(currentLatitude, currentLongitude);

        WeatherDto.Response regionWeather = weatherApiService.getRegionWeather(regionId);

        return new ResponseEntity<>(regionWeather, HttpStatus.OK);
    }
}

