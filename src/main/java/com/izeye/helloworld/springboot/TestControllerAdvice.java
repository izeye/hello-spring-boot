package com.izeye.helloworld.springboot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * {@link ControllerAdvice} for testing.
 *
 * @author Johnny Lim
 */
@ControllerAdvice
@Slf4j
public class TestControllerAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handle(Throwable ex) {
        log.error("Unexpected error.", ex);
    }

}
