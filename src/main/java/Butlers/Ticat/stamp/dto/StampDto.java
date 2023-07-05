package Butlers.Ticat.stamp.dto;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.stamp.entity.Stamp;
import lombok.*;

import java.util.List;

public class StampDto {
    @Getter
    @Setter
    public static class Post {
        private Long memberId;
        private Long contentId;
    }


    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response {
        private Long memberId;
        private List<FestivalDto.StampResponse> festivalList;
    }
}
