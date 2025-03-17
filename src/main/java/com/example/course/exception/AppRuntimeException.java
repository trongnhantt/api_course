package com.example.course.exception;

import com.example.course.util.constant.ExceptionType;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import com.example.course.util.constant.ExceptionType;

@Getter
public class AppRuntimeException extends RuntimeException {

    private final Integer errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    // Constructor receiving ExceptionType enum
    public AppRuntimeException(ExceptionType exceptionType) {
        super(exceptionType.getErrorMessage());
        this.errorCode = exceptionType.getErrorCode();
        this.errorMessage = exceptionType.getErrorMessage();
        this.httpStatus = exceptionType.getHttpStatus();
    }

    public AppRuntimeException(ExceptionType exceptionType, String customMessage) {
        super(customMessage);
        this.errorCode = exceptionType.getErrorCode();
        this.errorMessage = customMessage;
        this.httpStatus = exceptionType.getHttpStatus();
    }

    // Override toString to provide better debugging
    @Override
    public String toString() {
        return "AppRuntimeException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + getMessage() + '\'' +
                '}';
    }
}