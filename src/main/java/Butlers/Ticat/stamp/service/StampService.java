package Butlers.Ticat.stamp.service;

import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.repository.MemberRepository;
import Butlers.Ticat.stamp.entity.Stamp;
import Butlers.Ticat.stamp.repository.StampRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StampService {
    private final StampRepository stampRepository;
    private final FestivalRepository festivalRepository;
    private final MemberRepository memberRepository;


    public StampService(StampRepository stampRepository, FestivalRepository festivalRepository, MemberRepository memberRepository) {
        this.stampRepository = stampRepository;
        this.festivalRepository = festivalRepository;
        this.memberRepository = memberRepository;
    }


    public Stamp saveStamp(Long memberId, Long festivalId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Festival festival = festivalRepository.findByFestivalId(festivalId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FESTIVAL_NOT_FOUND));

        boolean isStampExists = stampRepository.existsByMemberAndFestival(member, festival);

        if (isStampExists) {
            throw new BusinessLogicException(ExceptionCode.STAMP_ALREADY_EXISTS);
        }

        Stamp stamp = new Stamp();

        stamp.setMember(member);
        stamp.setFestival(festival);
        stamp.setStampDate(LocalDate.now());

        return stampRepository.save(stamp);
    }
}
