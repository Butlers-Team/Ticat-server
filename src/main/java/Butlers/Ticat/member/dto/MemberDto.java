package Butlers.Ticat.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}
