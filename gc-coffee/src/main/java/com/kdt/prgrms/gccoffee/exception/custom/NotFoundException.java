package com.kdt.prgrms.gccoffee.exception.custom;

import org.springframework.http.HttpStatus;

public class NotFoundException extends RuntimeException {

    private String message;
    private HttpStatus status;

    public NotFoundException() {

    }

    public NotFoundException(HttpStatus status, String message) {

        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {

        return message;
    }

    public HttpStatus getStatus() {

        return status;
    }
}
