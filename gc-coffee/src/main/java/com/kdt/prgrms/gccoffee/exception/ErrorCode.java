package com.kdt.prgrms.gccoffee.exception;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

public enum ErrorCode {

    INVALID_INPUT_REQUEST(BAD_REQUEST, "입력 정보가 유효하지 않습니다."),

    CATEGORY_NOT_FOUNT(NOT_FOUND, "해당 카테고리를 찾을 수 없습니다."),

    SERVER_ERROR(INTERNAL_SERVER_ERROR, "해당 요청 수행을 실패했습니다.");

    private final HttpStatus status;
    private final String errorMessage;

    ErrorCode(HttpStatus status, String errorMessage) {

        this.status = status;
        this.errorMessage = errorMessage;
    }

    public HttpStatus getStatus() {

        return status;
    }

    public String getErrorMessage() {

        return errorMessage;
    }
}
