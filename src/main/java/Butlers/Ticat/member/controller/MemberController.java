package Butlers.Ticat.member.controller;


import Butlers.Ticat.calendar.dto.CalendarDto;
import Butlers.Ticat.calendar.entity.Calendar;
import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.dto.MultiResponseDto;
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
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Positive;
import java.util.Collections;
import java.util.List;

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
        memberService.joinInLocal(memberMapper.memberPostToMember(requestBody));

        return new ResponseEntity("회원가입이 완료되었습니다", HttpStatus.OK);
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




    // 프로필 이미지 업로드
    @PostMapping("/profile")
    public ResponseEntity postProfile(@RequestPart MultipartFile image) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        memberService.uploadProfileImage(authenticationMemberId, image);

        return new ResponseEntity<>("이미지 등록이 완료되었습니다.", HttpStatus.OK);
    }

    // 프로필 이미지 수정
    @PatchMapping("/profile")
    public ResponseEntity patchProfile(@RequestBody MultipartFile image) {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        memberService.updateProfileImage(authenticationMemberId, image);

        return new ResponseEntity<>("이미지 변경이 완료되었습니다.", HttpStatus.OK);
    }

    // 프로필 이미지 삭제
    @DeleteMapping("/profile")
    public ResponseEntity deleteProfile() {
        long authenticationMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        memberService.deleteProfileImage(authenticationMemberId);

        return new ResponseEntity<>("이미지 삭제가 완료되었습니다.", HttpStatus.NO_CONTENT);
    }



    @GetMapping("/members/{member-id}/calendar")
    public ResponseEntity getMemberCalendar(@PathVariable("member-id") @Positive Long memberId,
                                           @Positive @RequestParam int page,
                                           @RequestParam int year, @RequestParam int month,
                                           @RequestParam(required = false) Integer day) {
        Member member = memberService.findMember(memberId);

        Page<Calendar> calendarPage = memberService.getMemberCalendar(member, page -1, year, month, day);
        List<Calendar> calendars = calendarPage.getContent();

        List<CalendarDto.CalendarResponse> calendarResponses = memberMapper.getCalendarResponses(calendars);

        CalendarDto.Response calendarResponse = CalendarDto.Response.builder()
                .memberId(member.getMemberId())
                .festivalList(calendarResponses)
                .build();

        MultiResponseDto<CalendarDto.Response> multiResponseDto =
                new MultiResponseDto<>(Collections.singletonList(calendarResponse), calendarPage);
        return new ResponseEntity<>(multiResponseDto, HttpStatus.OK);
    }

    @GetMapping("/members/{member-id}/stamps")
    public ResponseEntity getMemberStamped(@PathVariable("member-id") @Positive Long memberId,
                                           @Positive @RequestParam int page,
                                           @RequestParam int year, @RequestParam int month,
                                           @RequestParam(required = false) Integer day) {
        Member member = memberService.findMember(memberId);

        Page<Stamp> stampPage = memberService.getMemberStamped(member, page -1, year, month, day);
        List<Stamp> stamps = stampPage.getContent();

        List<StampDto.StampResponse> stampResponses = memberMapper.getStampResponses(stamps);

        StampDto.Response stampResponse = StampDto.Response.builder()
                .memberId(member.getMemberId())
                .festivalList(stampResponses)
                .build();

        MultiResponseDto<StampDto.Response> multiResponseDto =
                new MultiResponseDto<>(Collections.singletonList(stampResponse), stampPage);
        return new ResponseEntity<>(multiResponseDto, HttpStatus.OK);
    }
}
