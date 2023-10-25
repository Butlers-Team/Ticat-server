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

        private String address;
        private String category;
        private String eventEndDate; // 행사 종료일
        private String eventhomepage;
        private String eventplace;
        private String eventStartDate; // 행사시작일
        private Long festivalId;
        private String image; // 축제 사진
        private boolean liked;
        private double mapx; // 축제 x좌표
        private double mapy; // 축제 y좌표
        private String overview; // 상세 정보
        private String playtime; //공연시간
        private String price; // 이용요금
        private DetailFestival.Status status;
        private String tel; // 축제 전화번호
        private String title;
        private Integer likeCount;
        private double reviewRating;
        private Integer reviewCount;
        private Long calendarId;
        private LocalDate scheduledDate;
        private LocalDate calendarDate;

    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        private Long festivalId;
        private LocalDate startDate;
        private LocalDate endDate;

        public Post(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }
    }
}
