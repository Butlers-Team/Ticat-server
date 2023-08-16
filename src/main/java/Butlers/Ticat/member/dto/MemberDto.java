package Butlers.Ticat.member.dto;

import Butlers.Ticat.stamp.dto.StampDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class MemberDto {

    @Getter
    @Setter
    public static class Post {

        @NotNull
        private String id;

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        // 비밀번호 확인 (검증 시 필요)
        @NotBlank
        private String confirmPassword;
        @NotBlank
        private String displayName;
    }

    @Getter
    @Setter
    public static class Patch {

        private String displayName;

    }

    @Getter
    @Builder
    public static class Response {

        private Long memberId;
        private String displayName;
        private String email;
        private String profileUrl;
        private String pureProfileUrl;
        private String social;

    }

    @Getter
    @Builder
    public static class stampResponse {
        private Long memberId;
        private List<StampDto.StampResponse> festivalList;
    }

    @Getter
    public static class id {
        @NotNull
        private String id;
    }

    @Getter
    public static class email {
        @Email
        private String email;
    }

    @Getter
    @Builder
    public static class ProfileUrl {
        private String profileUrl;
    }

    @Getter
    @Setter
    @Builder
    public static class recentFestivalResponse {
        private Long festivalId;
        private boolean isFavorite;
        private String title;
        private String address;
        private String eventStartDate;
        private String eventEndDate;
        private String imageUrl;
    }

    @Getter
    @Builder
    @Setter
    public static class recentResponse {
        private Long festivalId;
        private List<MemberDto.recentFestivalResponse> festivalResponseList;
    }
}
