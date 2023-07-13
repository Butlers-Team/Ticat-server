package Butlers.Ticat.calendar.repository;

import Butlers.Ticat.calendar.entity.Calendar;
import Butlers.Ticat.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    Page<Calendar> findByMemberAndCalendarDateIsBetween(Member member, LocalDate startDate, LocalDate endDate, Pageable pageable);


    Page<Calendar> findByMemberAndScheduleDateIsBetween(Member member, LocalDate startDate, LocalDate endDate, Pageable pageable);
}
