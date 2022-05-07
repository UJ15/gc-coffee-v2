package com.kdt.prgrms.gccoffee.exception;

import com.kdt.prgrms.gccoffee.dto.ProductResponse;
import com.kdt.prgrms.gccoffee.exception.custom.NotFoundException;
import com.kdt.prgrms.gccoffee.models.Category;
import com.kdt.prgrms.gccoffee.models.Hello;
import com.kdt.prgrms.gccoffee.models.OrderStatus;
import com.kdt.prgrms.gccoffee.models.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.kdt.prgrms.gccoffee.exception.ErrorResponse;

import java.time.LocalDateTime;

import static com.kdt.prgrms.gccoffee.exception.ErrorCode.INVALID_INPUT_REQUEST;
import static com.kdt.prgrms.gccoffee.exception.ErrorCode.NOT_FOUNT;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({MethodArgumentNotValidException.class, IllegalArgumentException.class})
    public ResponseEntity<?> handleMethodArgumentValidException() {

        logger.error("MethodArgumentValidException : {}", INVALID_INPUT_REQUEST.getErrorMessage());
        ResponseEntity<ErrorResponse> result = ErrorResponse.toResponseEntity(INVALID_INPUT_REQUEST);

        return ErrorResponse.toResponseEntity(INVALID_INPUT_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException() {

        logger.error("NotFoundException : {}", NOT_FOUNT);
        return ErrorResponse.toResponseEntity(NOT_FOUNT);
    }
}
