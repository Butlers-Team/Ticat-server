package Butlers.Ticat.stamp.service;

import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.festival.entity.DetailFestival;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.repository.MemberRepository;
import Butlers.Ticat.stamp.dto.StampDto;
import Butlers.Ticat.stamp.entity.Location;
import Butlers.Ticat.stamp.entity.Stamp;
import Butlers.Ticat.stamp.repository.StampRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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


    public List<Festival> getNearbyFestivals(Location clientLocation) {
        List<Festival> allFestivals = festivalRepository.findAll();
        List<Festival> nearbyFestivals = new ArrayList<>();

        for (Festival festival : allFestivals) {
            if (isWithin500m(clientLocation, festival.getLocation())) {
                nearbyFestivals.add(festival);
            }
        }

        return nearbyFestivals;
    }

    private boolean isWithin500m(Location location1, Location location2) {

        double distance = location1.distanceTo(location2);
        return distance <= 500; // 거리
    }

    public void stampFestivalsWithin500m(Location memberLocation, Member member) {
        List<Festival> nearbyFestivals = getNearbyFestivals(memberLocation);

        for (Festival festival : nearbyFestivals) {
            Stamp stamp = Stamp.builder()
                    .festival(festival)
                    .member(member)
                    .build();

            stampRepository.save(stamp);
        }
    }

    public Stamp saveStamp(Long memberId, Long contentId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        Festival festival = festivalRepository.findByContentId(contentId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FESTIVAL_NOT_FOUND));

        Stamp stamp = new Stamp();

        stamp.setMember(member);
        stamp.setFestival(festival);

        DetailFestival detailFestival = festival.getDetailFestival();
        if (detailFestival != null) {

            stamp.setTitle(festival.getTitle());
            stamp.setAddress(festival.getAddress());
            stamp.setEventDate(detailFestival.getEventDate());
        }

        return stampRepository.save(stamp);
    }

    public Stamp getStamp(Long memberId) {
        Stamp stamp = stampRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.STAMP_NOT_FOUND));
        return stamp;
    }

    public Page<Festival> findFestivals(int page, int size, Integer year, Integer month) {
        PageRequest pageRequest = PageRequest.of(page - 1, size);

        if (year != null && month != null) {
            YearMonth searchYearMonth = YearMonth.of(year, month);
            return festivalRepository.findByYearAndMonth(searchYearMonth, pageRequest);
        } else {
            return festivalRepository.findAll(pageRequest);
        }
    }
}
