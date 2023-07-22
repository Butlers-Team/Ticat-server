package Butlers.Ticat.interest.controller;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.interest.dto.InterestDto;
import Butlers.Ticat.interest.entity.Interest;
import Butlers.Ticat.interest.mapper.InterestMapper;
import Butlers.Ticat.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InterestController {

    private final InterestService interestService;
    private final InterestMapper interestMapper;

    @PostMapping("/interest")
    public ResponseEntity postInterest(@RequestBody InterestDto.Post requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        Interest interest = interestService.registerInterest(interestMapper.postToInterest(authenticationMemberId, requestBody));

        return new ResponseEntity<>(interestMapper.interestToPostResponse(interest), HttpStatus.OK);
    }
}
