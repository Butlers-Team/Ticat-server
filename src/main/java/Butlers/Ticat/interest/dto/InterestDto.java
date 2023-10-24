package Butlers.Ticat.interest.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

public class InterestDto {

    @Getter
    public static class Post {

        @NotBlank
        @Pattern(regexp = "^[A-Za-z가-힣0-9]{2,8}$")
        // 닉네임 등록과 관심사 등록을 같은 메서드에서 진행하므로 포함
        private String displayName;
        private List<String> categories;
    }

    @Getter
    @Builder
    public static class PostResponse {
        private String displayName;
    }

    @Getter
    public static class Patch {
        private List<String> categories;
    }

    @Getter
    @Builder
    public static class Response {
        private List<String> categories;
    }
}
