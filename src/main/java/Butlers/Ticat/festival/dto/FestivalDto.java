package Butlers.Ticat.festival.dto;
import Butlers.Ticat.festival.entity.DetailFestival;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
public class FestivalDto {

    @Getter
    @Setter
    public static class ListResponse{

        private Long contentId;
        private String status;
        private String title;
        private String image;
        private String address;
        private String category;
        private String eventstartdate; // 행사시작일

        private String eventenddate; // 행사 종료일
        private double reviewRating;
        private Integer reviewCount;
        private Integer likeCount;
        private String area;
        private double mapx;
        private double mapy;
    }

    @Getter
    @Setter
    public static class Response{

        private Long contentId;
        private String status;
        private String eventstartdate; // 행사시작일

        private String eventenddate; // 행사 종료일
        private String image;
        private String title;
        private String address;
        private String overview;
        private String tel;
        private String eventhomepage;
        private String eventplace; // 이게 비어있다면 주소 그대로 노출
        private String price;
        private String playtime;
        private double mapx;
        private double mapy;
    }


    @Getter
    @Setter
    @Builder
    public static class StampResponse{

        private Long contentId;
        private DetailFestival.Status status;
        private LocalDate stampDate;
        private String eventStartDate; // 행사시작일
        private String eventEndDate; // 행사 종료일
        private String title;
        private String address;
    }
}
