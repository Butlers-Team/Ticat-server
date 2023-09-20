package Butlers.Ticat.calendar.controller;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.calendar.dto.CalendarDto;
import Butlers.Ticat.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarService calendarService;

    @PostMapping("/save")
    public ResponseEntity createdCalendar(@RequestBody CalendarDto.Post requestBody) {
        Long festivalId = requestBody.getFestivalId();
        long jwtMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        LocalDate startDate = requestBody.getStartDate();
        LocalDate endDate = requestBody.getEndDate();

        calendarService.createCalendar(festivalId, jwtMemberId, startDate, endDate);
        return ResponseEntity.ok("일정이 등록되었습니다");
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity deletedCalendar(@PathVariable("calendarId") Long calendarId) {

        long jwtMemberId = JwtParseInterceptor.getAuthenticatedMemberId();
        calendarService.deleteCalendarById(calendarId, jwtMemberId);

        return new ResponseEntity<>("일정이 삭제 되었습니다",HttpStatus.OK);
    }



}
