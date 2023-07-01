package Butlers.Ticat.festival.dto;
import lombok.Getter;
import lombok.Setter;

public class FestivalDto {

    @Getter
    @Setter
    public static class ListResponse{

        private String contentId;
        private String title;
        private String image;
        private String eventDate;
        private Double rating;
        private Integer like;
    }
}
