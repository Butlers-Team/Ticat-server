package Butlers.Ticat.calendar.service;

import Butlers.Ticat.calendar.entity.Calendar;
import Butlers.Ticat.calendar.repository.CalendarRepository;
import Butlers.Ticat.exception.BusinessLogicException;
import Butlers.Ticat.exception.ExceptionCode;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final MemberService memberService;
    private final FestivalRepository festivalRepository;
    public List<Calendar> createCalendar(Long festivalId, Long memberId, LocalDate startDate, LocalDate endDate) {


        Member member = memberService.findMember(memberId);

        Festival festival = festivalRepository.findByFestivalId(festivalId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FESTIVAL_NOT_FOUND));

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("시작날짜와 마지막날짜를 입력해주세요");
        }

        boolean isDuplicate = calendarRepository.existsByFestivalAndMemberAndScheduleDateBetween(festival, member, startDate, endDate);
        if (isDuplicate) {
            throw new BusinessLogicException(ExceptionCode.DUPLICATE_CALENDAR_REGISTRATION);
        }

        List<LocalDate> datesToSave = new ArrayList<>();
        LocalDate currentDate = startDate;

        while (!currentDate.isAfter(endDate)) {
            datesToSave.add(currentDate);
            currentDate = currentDate.plusDays(1);
        }

        List<Calendar> calendarsToSave = datesToSave.stream()
                .map(data -> {
                            Calendar calendar = new Calendar();
                            calendar.setFestival(festival);
                            calendar.setMember(member);
                            calendar.setCalendarDate(LocalDate.now());
                            calendar.setScheduleDate(data);
                            return calendar;
                        })
                .collect(Collectors.toList());

        return calendarRepository.saveAll(calendarsToSave);
    }


    public void deleteCalendarById(Long festivalId, Long memberId) {

        memberService.findMember(memberId);

        Calendar calendar = findVerifiedCalendar(festivalId);

        if (calendar.getMember().getMemberId().equals(memberId)) {
            calendarRepository.delete(calendar);
        } else {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED);
        }

    }


    public Calendar findVerifiedCalendar(Long festivalId) {

        Optional<Calendar> optionalCalendar = calendarRepository.findById(festivalId);

        return optionalCalendar.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CALENDAR_NOT_FOUND));
    }

    public Calendar findCalendar(Long festivalId) {

        return calendarRepository.findById(festivalId).get();
    }

}
