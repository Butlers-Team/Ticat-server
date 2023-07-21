package Butlers.Ticat.festival.dto;

import lombok.Getter;
import lombok.Setter;
public class FestivalDto {

    @Getter
    @Setter
    public static class ListResponse{
        private Long festivalId;
        private String status;
        private String title;
        private String image;
        private String address;
        private String category;
        private String eventstartdate;
        private String eventenddate;
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
        private Long festivalId;
        private String category;
        private String status;
        private String eventstartdate;
        private String eventenddate;
        private String image;
        private String title;
        private String address;
        private String overview;
        private String tel;
        private String eventhomepage;
        private String eventplace;
        private String price;
        private String playtime;
        private double mapx;
        private double mapy;
        private boolean isLiked;
    }

    @Getter
    @Setter
    public static class DistanceResponse {
        private double km;
    }

}
