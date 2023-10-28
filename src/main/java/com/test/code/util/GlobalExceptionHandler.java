package com.test.code.util;

import com.test.code.exception.BookException;
import com.test.code.exception.UserException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BookException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public Map<String, String> handleException(BookException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        return errorMap;
    }

    @ExceptionHandler(UserException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public Map<String, String> handleException(UserException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        return errorMap;
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public Map<String, String> handleException(BadCredentialsException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        return errorMap;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Map<String, String> handleException(ConstraintViolationException e) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        return errorMap;
    }
}
