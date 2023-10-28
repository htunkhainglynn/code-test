package com.test.code.util;

import com.test.code.exception.BookException;
import com.test.code.security.InvalidJwtAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(BookException e) {
        Map<String, String> errorMap = Map.of("message", e.getMessage());
        return errorMap.toString();
    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public String handleException(InvalidJwtAuthenticationException e) {
        Map<String, String> errorMap = Map.of("message", e.getMessage());
        return errorMap.toString();
    }

}
