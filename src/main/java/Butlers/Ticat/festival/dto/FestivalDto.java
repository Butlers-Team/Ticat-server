package Butlers.Ticat.festival.dto;
import lombok.Getter;
import lombok.Setter;

public class FestivalDto {

    @Getter
    @Setter
    public static class ListResponse{

        private Long contentId;
        private String title;
        private String image;
        private String address;
        private String category;
        private String eventDate;
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
        private String eventDate;
        private String image;
        private String title;
        private String address;
        private String overview;
        private String tel;
        private String eventhomepage;
        private String eventplace; // 이게 비어있다면 주소 그대로 노출
        private String usetimefestival;
        private String playtime;
        private double mapx;
        private double mapy;
    }
}
