package Butlers.Ticat.calendar.dto;

import Butlers.Ticat.festival.dto.FestivalDto;
import lombok.*;

import java.util.List;

public class CalendarDto {

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private List<FestivalDto.CalendarResponse> festivalList;
    }
}
