package Butlers.Ticat.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다"),
    MEMBER_EMAIL_EXISTS(409, "이메일이 이미 있습니다" ),

    LIKE_NOT_TWICE(409,"좋아요를 두 번 할 수 없습니다"),

    PASSWORD_DOES_NOT_MATCH(409, "입력하신 비밀번호가 일치하지 않습니다."),

    LIKE_NOT_CANCEL(204,"좋아요를 취소할 수 없습니다");
    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
