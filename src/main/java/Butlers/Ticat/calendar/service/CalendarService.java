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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final MemberService memberService;
    private final FestivalRepository festivalRepository;
    public Calendar createCalendar(Long festivalId, Long memberId) {

        Member member = memberService.findMember(memberId);

        Festival festival = festivalRepository.findByFestivalId(festivalId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.FESTIVAL_NOT_FOUND));

        Calendar calendar = new Calendar();
        calendar.setFestival(festival);
        calendar.setMember(member);
        calendar.setCalendarDate(LocalDate.now());

        return calendarRepository.save(calendar);
    }

    public void deleteCalendarById(Long calendarId) {

        Calendar calendar = findVerifiedCalendar(calendarId);
        calendarRepository.delete(calendar);
    }


    public Calendar findVerifiedCalendar(Long calendarId) {

        Optional<Calendar> optionalCalendar = calendarRepository.findById(calendarId);

        return optionalCalendar.orElseThrow(() -> new BusinessLogicException(ExceptionCode.CALENDAR_NOT_FOUND));
    }

    public Calendar findCalendar(Long calendarId) {

        return calendarRepository.findById(calendarId).get();
    }

}
