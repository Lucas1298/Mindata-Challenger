package com.project.challenge.application.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final HttpStatus httpStatus;
    private String error;

    public ApiException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiException(HttpStatus httpStatus, String message, String error) {
        super(message);
        this.httpStatus = httpStatus;
        this.error = error;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
