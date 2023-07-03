package Butlers.Ticat.blog.controller;

import Butlers.Ticat.blog.dto.NaverBlogDto;
import Butlers.Ticat.blog.service.NaverBlogSearchApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/naverblog")
@RequiredArgsConstructor
public class NaverBlogController {

    private final NaverBlogSearchApiService api;

    @GetMapping
    public ResponseEntity getFood(@RequestParam String festival) throws JsonProcessingException {
        List<NaverBlogDto.Response> blogPosts = api.search(festival);

        return new ResponseEntity<>(blogPosts, HttpStatus.OK);
    }


}
