package Butlers.Ticat.member.service;

import Butlers.Ticat.aws.service.AwsS3Service;
import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 로컬 회원 가입
    public void joinInLocal(Member member) {
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        memberRepository.save(member);
    }

    // 오어스 회원 가입
    public Member joinInOauth(Member member) {
        verifyExistingEmail(member.getEmail());
        member.setOauthChecked(true);

        return memberRepository.save(member);
    }

    // 오어스 로그인 중 joinInOauth 에서 MEMBER_EMAIL_EXISTS 예외 발생 시 사용 될 메서드
    public Member findMemberByEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 프로필 이미지 등록
    public void uploadProfileImage(Long memberId, MultipartFile file) {
        Member member = findVerifiedMember(memberId);
        String[] uriList = awsS3Service.uploadFile(file);
        member.setProfileUrl(uriList[0]);
        member.setPureProfileUrl(uriList[1]);

        memberRepository.save(member);
    }

    // 프로필 이미지 수정
    public void updateProfileImage(Long memberId, MultipartFile file) {
        Member member = findVerifiedMember(memberId);
        if (member.getProfileUrl() != null) {
            awsS3Service.deleteFile(member.getPureProfileUrl());
        }

        // 코드가 중복 되나 uploadProfileImage 메서드를 사용하면 db select 가 한 번 더 발생하게 된다고 판단
        String[] urlList = awsS3Service.uploadFile(file);
        member.setProfileUrl(urlList[0]);
        member.setPureProfileUrl(urlList[1]);

        memberRepository.save(member);
    }

    // 프로필 이미지 삭제
    public void deleteProfileImage(Long memberId) {
        Member member = findVerifiedMember(memberId);
        if (member.getProfileUrl() != null) {
            awsS3Service.deleteFile(member.getPureProfileUrl());
            member.setProfileUrl(null);
            member.setPureProfileUrl(null);

            memberRepository.save(member);
        }
    }

    public Member findVerifiedMember (Long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findedMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findedMember;
    }

    public Member findMember(Long memberId) {
        return findVerifiedMember(memberId);
    }

    private void verifyExistingEmail(String email) {
        Optional<Member> foundMemberByEmail = memberRepository.findByEmail(email);
        if (foundMemberByEmail.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EMAIL_EXISTS);
        }
    }

    public Member updateMember(Member member) {
        Member findedMember = findVerifiedMember(member.getMemberId());
        verifyExistingEmail(member.getEmail());

        Optional.ofNullable(member.getDisplayName())
                .ifPresent(displayName -> findedMember.setDisplayName(displayName));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findedMember.setPassword(password));
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findedMember.setEmail(email));

        return memberRepository.save(findedMember);
    }

    public void deleteMember(Long memberId) {
        Member findedMember = findVerifiedMember(memberId);
        memberRepository.delete(findedMember);
    }
}
