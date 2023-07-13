package Butlers.Ticat.calendar.controller;

import Butlers.Ticat.calendar.dto.CalendarDto;
import Butlers.Ticat.calendar.mapper.CalendarMapper;
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
    private final CalendarMapper calendarMapper;
    private final CalendarService calendarService;

    @PostMapping("/save")
    public ResponseEntity createdCalendar(@RequestBody CalendarDto.Post requestBody) {
        try {
        Long festivalId = requestBody.getFestivalId();
        Long memberId = requestBody.getMemberId();
        LocalDate scheduleDate = requestBody.getScheduleDate();

        calendarService.createCalendar(festivalId, memberId, scheduleDate);
        return ResponseEntity.ok("일정이 등록되었습니다");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("일정 등록 중 오류가 발생했습니다");
        }
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity deletedCalendar(@PathVariable("calendarId") Long calendarId) {
        calendarService.deleteCalendarById(calendarId);

        return new ResponseEntity<>("일정이 삭제 되었습니다",HttpStatus.OK);
    }



}
