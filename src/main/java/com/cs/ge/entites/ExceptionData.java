package com.cs.ge.entites;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ExceptionData {
    private  String message;
    private  Throwable throwable;
    private  HttpStatus httpStatus;
    private  LocalDateTime timestamp;

    public ExceptionData() {
    }

    public ExceptionData(String message, Throwable throwable, HttpStatus httpStatus, LocalDateTime timestamp) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
