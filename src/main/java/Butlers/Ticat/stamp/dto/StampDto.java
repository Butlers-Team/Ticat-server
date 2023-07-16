package Butlers.Ticat.stamp.dto;

import Butlers.Ticat.festival.entity.DetailFestival;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

public class StampDto {
    @Getter
    @Setter
    public static class Post {
        private Long memberId;
        private Long festivalId;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private List<StampDto.StampResponse> festivalList;
    }

    @Getter
    @Setter
    @Builder
    public static class StampResponse{

        private Long festivalId;
        private String category;
        private DetailFestival.Status status;
        private LocalDate stampDate;
        private String eventStartDate; // 행사시작일
        private String eventEndDate; // 행사 종료일
        private String title;
        private String address;
    }
}
