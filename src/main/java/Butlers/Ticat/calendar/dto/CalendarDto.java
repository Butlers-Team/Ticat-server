package Butlers.Ticat.calendar.dto;

import Butlers.Ticat.festival.entity.DetailFestival;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class CalendarDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private List<CalendarResponse> festivalList;
    }

    @Getter
    @Setter
    @Builder
    public static class CalendarResponse{

        private Long contentId;
        private String category;
        private DetailFestival.Status staus;
        private LocalDate calendarDate;
        private String eventStartDate; // 행사시작일
        private String eventEndDate; // 행사 종료일
        private String title;
        private String address;
    }
}
