package Butlers.Ticat.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다"),
    MEMBER_EMAIL_EXISTS(409, "이메일이 이미 있습니다" ),
    FESTIVAL_NOT_FOUND(404, "축제를 찾을 수 없습니다"),
    STAMP_NOT_FOUND(404, "스탬프를 찾을 수 없습니다");

    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
