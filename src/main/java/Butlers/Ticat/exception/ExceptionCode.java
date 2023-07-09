package Butlers.Ticat.exception;

import lombok.Getter;

public enum ExceptionCode {

    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다"),
    MEMBER_EMAIL_EXISTS(409, "해당 이메일 주소는 이미 사용 중입니다."),
    FESTIVAL_NOT_FOUND(404, "축제를 찾을 수 없습니다"),
    STAMP_NOT_FOUND(404, "스탬프를 찾을 수 없습니다"),
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
