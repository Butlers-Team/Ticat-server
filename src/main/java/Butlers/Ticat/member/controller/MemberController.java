package Butlers.Ticat.member.controller;

import Butlers.Ticat.dto.MultiResponseDto;
import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.member.dto.MemberDto;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.mapper.MemberMapper;
import Butlers.Ticat.member.service.MemberService;
import Butlers.Ticat.stamp.dto.StampDto;
import Butlers.Ticat.stamp.entity.Stamp;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper memberMapper;

    @PostMapping("/signup")
    public ResponseEntity createdMember(@RequestBody MemberDto.Post requestBody) {
        Member member = memberMapper.memberPostToMember(requestBody);
        memberService.createMember(member);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/members/{member-id}")
    public ResponseEntity findMember(@PathVariable("member-id") Long memberId) {

        return new ResponseEntity<>(memberMapper.memberToMemberResponse(
                memberService.findMember(memberId)), HttpStatus.OK);
    }

    @PatchMapping("/members/{member-id}")
    public ResponseEntity updateMember(@PathVariable("member-id") Long memberId,
                                       @RequestBody MemberDto.Patch requestBody) {
        requestBody.setMemberId(memberId);

        Member member = memberMapper.memberPatchToMember(requestBody);

        memberService.updateMember(member);

        return new ResponseEntity<>(memberMapper.memberToMemberResponse(
                memberService.findMember(memberId)), HttpStatus.OK);
    }

    @DeleteMapping("/members/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") Long memberId) {
        memberService.deleteMember(memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/members/{member-id}/stamps")
    public ResponseEntity getMemberStamped(@PathVariable("member-id") @Positive Long memberId,
                                           @Positive @RequestParam int page,
                                           @RequestParam int year, @RequestParam int month,
                                           @RequestParam(required = false) Integer day) {
        Member member = memberService.findMember(memberId);
        Page<Stamp> stampPage = memberService.getMemberStamped(member, page, year, month, day);
        List<Stamp> stamps = stampPage.getContent();

        List<FestivalDto.StampResponse> stampResponses = memberMapper.getResponses(stamps);

        StampDto.Response stampResponse = StampDto.Response.builder()
                .memberId(member.getMemberId())
                .festivalList(stampResponses)
                .build();

        MultiResponseDto<StampDto.Response> multiResponseDto =
                new MultiResponseDto<>(Collections.singletonList(stampResponse), stampPage);
        return new ResponseEntity<>(multiResponseDto, HttpStatus.OK);
    }

}
