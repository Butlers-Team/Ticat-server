package Butlers.Ticat.interest.controller;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.interest.dto.InterestDto;
import Butlers.Ticat.interest.entity.Interest;
import Butlers.Ticat.interest.mapper.InterestMapper;
import Butlers.Ticat.interest.service.InterestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/interest")
    public ResponseEntity patchInterest(@RequestBody InterestDto.Patch requestBody) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        Interest interest = interestService.updateInterest(interestMapper.patchToInterest(requestBody), authenticationMemberId);

        return new ResponseEntity<>(interestMapper.interestToResponse(interest), HttpStatus.OK);
    }

    @GetMapping("/interest")
    public ResponseEntity getInterest() {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        Interest interest = interestService.getInterestByMemberId(authenticationMemberId);

        return new ResponseEntity<>(interestMapper.interestToResponse(interest), HttpStatus.OK);
    }
}
