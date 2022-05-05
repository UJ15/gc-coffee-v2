package com.kdt.prgrms.gccoffee.exception;

import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    private ErrorResponse(Builder builder) {
        this.status = builder.status;
        this.error = builder.error;
        this.code = builder.code;
        this.message = builder.message;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ErrorResponse.builder()
                        .status(errorCode.getStatus().value())
                        .error(errorCode.getStatus().name())
                        .code(errorCode.name())
                        .message(errorCode.getErrorMessage())
                        .build());
    }

    public static class Builder {

        private int status;
        private String error;
        private String code;
        private String message;

        private Builder status(int value) {

            this.status = value;
            return this;
        }

        private Builder error(String value) {

            this.error = value;
            return this;
        }

        private Builder code(String value) {

            this.code = value;
            return this;
        }

        private Builder message(String value) {

            this.code = value;
            return this;
        }

        private ErrorResponse build() {

            return new ErrorResponse(this);
        }

    }

    public static Builder builder() {

        return new Builder();
    }

}
