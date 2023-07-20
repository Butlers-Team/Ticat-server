package Butlers.Ticat.exception;

import lombok.Getter;

public enum ExceptionCode {
    MEMBER_NOT_FOUND(404, "회원을 찾을 수 없습니다"),
    MEMBER_EMAIL_EXISTS(409, "현재 사용중인 이메일 입니다." ),
    MEMBER_ID_EXISTS(409, "중복된 아이디가 존재합니다"),
    FESTIVAL_NOT_FOUND(404, "축제를 찾을 수 없습니다"),
    REGION_NOT_FOUND(404, "지역을 찾을 수 없습니다"),
    STAMP_NOT_FOUND(404, "스탬프를 찾을 수 없습니다"),
    LIKE_NOT_TWICE(409,"좋아요를 두 번 할 수 없습니다"),
    PASSWORD_DOES_NOT_MATCH(409, "입력하신 비밀번호가 일치하지 않습니다."),
    CANT_UPLOAD_MORE_THAN_FOUR(409, "사진은 4개 초과해서 업로드 할 수 없습니다."),
    AVAILABLE_AFTER_LOGIN(403, "로그인 후 이용가능합니다."),
    REVIEW_NOT_FOUND(404, "해당 리뷰를 찾을 수 없습니다."),
    REVIEW_COMMENT_NOT_FOUND(404, "해당 댓글을 찾을 수 없습니다."),
    ONLY_AUTHOR(403, "리뷰 작성자만 수정이 가능합니다."),
    LIKE_NOT_CANCEL(204,"좋아요를 취소할 수 없습니다"),
    CALENDAR_NOT_FOUND(404,"일정을 찾을 수 없습니다"),
    STAMP_ALREADY_EXISTS(409, "스탬프가 이미 저장되어있습니다" ),
    DUPLICATE_CALENDAR_REGISTRATION(409, "동일 축제를 같은날 중복 등록 할 수 없습니다"),
    UNAUTHORIZED(404, "권한이 없습니다" );
    @Getter
    private int status;
    @Getter
    private String message;

    ExceptionCode(int code, String message) {
        this.status = code;
        this.message = message;
    }
}
