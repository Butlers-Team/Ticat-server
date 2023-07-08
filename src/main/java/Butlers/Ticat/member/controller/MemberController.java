package Butlers.Ticat.member.controller;

import Butlers.Ticat.member.dto.MemberDto;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.mapper.MemberMapper;
import Butlers.Ticat.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
