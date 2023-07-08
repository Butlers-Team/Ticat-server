package Butlers.Ticat.member.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

public class MemberDto {

    @Getter
    @Setter
    public static class Post {

        @Email
        @NotBlank
        private String email;

        @NotBlank
        private String password;

        // 비밀번호 확인 (검증 시 필요)
        @NotBlank
        private String confirmPassword;

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
        private List<String> categories;
    }
}
