package Butlers.Ticat.member.dto;

import Butlers.Ticat.festival.dto.FestivalDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class MemberDto {

    @Getter
    @Setter
    public static class Post {
        private String displayName;
        private String password;
        private String email;

    }

    @Getter
    @Setter
    public static class Patch {

        private Long memberId;
        private String displayName;
        private String password;

    }

    @Getter
    @Builder
    public static class Response {

        private Long memberId;
        private String displayName;

    }

    @Getter
    @Builder
    public static class stampResponse {
        private Long memberId;
        private List<FestivalDto.StampResponse> festivalList;
    }
}
