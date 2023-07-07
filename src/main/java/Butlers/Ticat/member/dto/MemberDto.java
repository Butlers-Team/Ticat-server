package Butlers.Ticat.member.dto;

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
    public static class Interest {

        // 닉네임 등록과 관심사 등록을 같은 메서드에서 진행하므로 포함
        private String displayName;
        private List<String> interests;
    }
}
