package Butlers.Ticat.calendar.controller;

import Butlers.Ticat.calendar.mapper.CalendarMapper;
import Butlers.Ticat.calendar.service.CalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/calendar")
@RequiredArgsConstructor
public class CalendarController {
    private final CalendarMapper calendarMapper;
    private final CalendarService calendarService;

    @PostMapping("/save")
    public ResponseEntity createdCalendar(@RequestParam Long contentId,
                                          @RequestParam Long memberId) {

        calendarService.createCalendar(contentId, memberId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{calendarId}")
    public ResponseEntity deletedCalendar(@PathVariable("calendarId") Long calendarId) {
        calendarService.deleteCalendarById(calendarId);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
