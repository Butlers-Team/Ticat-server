package Butlers.Ticat.interest.controller;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.interest.dto.InterestDto;
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
    public ResponseEntity postInterest(@RequestBody InterestDto.Post interest) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        interestService.registerInterest(interestMapper.postToInterest(authenticationMemberId, interest));

        return new ResponseEntity<>("관심사 등록이 완료되었습니다.", HttpStatus.OK);
    }

    @PatchMapping("/interest")
    public ResponseEntity patchInterest(@RequestBody InterestDto.Patch interest) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        interestService.modifyInterest(interestMapper.patchToInterest(authenticationMemberId, interest));

        return new ResponseEntity<>("관심사 수정이 완료되었습니다.", HttpStatus.OK);
    }
}
