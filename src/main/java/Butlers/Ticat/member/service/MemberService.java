package Butlers.Ticat.member.service;


import Butlers.Ticat.calendar.entity.Calendar;
import Butlers.Ticat.calendar.repository.CalendarRepository;
import Butlers.Ticat.aws.service.AwsS3Service;
import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import Butlers.Ticat.interest.entity.Interest;
import Butlers.Ticat.interest.repository.InterestRepository;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.entity.MemberRecent;
import Butlers.Ticat.member.repository.MemberRepository;
import Butlers.Ticat.stamp.entity.Stamp;
import Butlers.Ticat.stamp.repository.StampRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final AwsS3Service awsS3Service;
    private final MemberRepository memberRepository;
    private final CalendarRepository calendarRepository;
    private final StampRepository stampRepository;
    private final InterestRepository interestRepository;
    private final PasswordEncoder passwordEncoder;
    private final FestivalRepository festivalRepository;

    // 로컬 회원 가입
    public void joinInLocal(Member member) {
        verifyExistingId(member.getId());
        verifyExistingEmail(member.getEmail());
        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);
        member.setDisplayName(member.getDisplayName());

        Interest interest = new Interest();
        interest.setMember(memberRepository.save(member));

        interestRepository.save(interest);
    }

    // 아이디 중복 획인
    public void verifyExistingId(String id) {
        Optional<Member> optionalMember = memberRepository.findById(id);
        if (optionalMember.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_ID_EXISTS);
        }
    }

    // 로컬 회원가입 시 이메일 중복 확인
    // false -> 로컬 회원가입
    public void verifyExistingEmail(String email) {
        Optional<Member> foundMemberByEmail = memberRepository.findByEmailAndIsOauthChecked(email, false);
        if (foundMemberByEmail.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_EMAIL_EXISTS);
        }
    }

    // 오어스 회원 가입
    public Member joinInOauth(Member member) {
        member.setOauthChecked(true);
        verifyExistingId(member.getId());

        Member savedMember = memberRepository.save(member);
        Interest interest = new Interest();
        interest.setMember(savedMember);

        interestRepository.save(interest);
        return savedMember;
    }

    // 오어스 로그인 중 joinInOauth 에서 MEMBER_EMAIL_EXISTS 예외 발생 시 사용 될 메서드
    public Member findMemberById(String id) {
        Optional<Member> optionalMember = memberRepository.findById(id);

        return optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 프로필 이미지 등록
    public Member uploadProfileImage(Long memberId, MultipartFile file) {
        Member member = findVerifiedMember(memberId);
        String[] urlList = awsS3Service.uploadFile(file);
        member.setProfileUrl(urlList[0]);
        member.setPureProfileUrl(urlList[1]);

        return memberRepository.save(member);
    }

    // 프로필 이미지 수정
    public Member updateProfileImage(Long memberId, MultipartFile file) {
        Member member = findVerifiedMember(memberId);
        if (member.getPureProfileUrl() != null) {
            awsS3Service.deleteFile(member.getPureProfileUrl());
        }

        // 코드가 중복 되나 uploadProfileImage 메서드를 사용하면 db select 가 한 번 더 발생하게 된다고 판단
        String[] urlList = awsS3Service.uploadFile(file);
        member.setProfileUrl(urlList[0]);
        member.setPureProfileUrl(urlList[1]);

        return memberRepository.save(member);
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

    public Member updateMember(Member member) {
        Member findedMember = findVerifiedMember(member.getMemberId());
        verifyExistingEmail(member.getEmail());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());


        Optional.ofNullable(member.getDisplayName())
                .ifPresent(displayName -> findedMember.setDisplayName(displayName));
        Optional.ofNullable(encryptedPassword)
                .ifPresent(password -> findedMember.setPassword(password));
        Optional.ofNullable(member.getEmail())
                .ifPresent(email -> findedMember.setEmail(email));

        return memberRepository.save(findedMember);
    }

    public void deleteMember(Long memberId) {
        Member findedMember = findVerifiedMember(memberId);
        memberRepository.delete(findedMember);
    }


    public Page<Calendar> getMemberCalendar(Member member, int page, int year, int month, Integer day) {

        LocalDate startDate;
        LocalDate endDate;

        if (day != null) {
            startDate = LocalDate.of(year, month, day);
            endDate = startDate;
        } else {
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        }

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "calendarDate"));
        return calendarRepository.findByMemberAndCalendarDateIsBetween(member, startDate, endDate, pageable);
    }

    public List<Stamp> getMemberStamped(Member member, int year, int month, Integer day) {

        LocalDate startDate;
        LocalDate endDate;

        if (day != null) {
            startDate = LocalDate.of(year, month, day);
            endDate = startDate.plusDays(1);
        } else {
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.plusMonths(1);
        }

        return stampRepository.findByMemberAndStampDateBetween(member, startDate, endDate);
    }


    public Page<Calendar> getMemberSchedule(Member member, int page, int year, int month, Integer day) {
        LocalDate startDate;
        LocalDate endDate;

        if (day != null) {
            startDate = LocalDate.of(year, month, day);
            endDate = startDate;
        } else {
            startDate = LocalDate.of(year, month, 1);
            endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        }

        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "scheduleDate"));
        return calendarRepository.findByMemberAndScheduleDateIsBetween(member, startDate, endDate, pageable);
    }

    public void addRecentFestival(Member member, Long festivalId) {
        Optional<Festival> optionalFestival = festivalRepository.findByFestivalId(festivalId);

        if (optionalFestival.isPresent()) {
            Festival festival = optionalFestival.get();
            MemberRecent memberRecent = new MemberRecent(festival);
            member.addMemberRecent(memberRecent);

            memberRepository.save(member);
        } else {
            throw new BusinessLogicException(ExceptionCode.FESTIVAL_NOT_FOUND);
        }
    }

    public List<MemberRecent> getRecentFestival(Member member) {
        List<MemberRecent> memberRecentList = member.getMemberRecentList();
        List<MemberRecent> recentFestivals = new ArrayList<>();
        Set<Long> festivalIds = new HashSet<>();

        for (int i = memberRecentList.size() - 1; i >= 0 && recentFestivals.size() < 5; i--) {
            MemberRecent recent = memberRecentList.get(i);
            Long festivalId = recent.getFestival().getFestivalId();
            if (!festivalIds.contains(festivalId)) {
                recentFestivals.add(recent);
                festivalIds.add(festivalId);
            }
        }

        return recentFestivals;
    }

}
