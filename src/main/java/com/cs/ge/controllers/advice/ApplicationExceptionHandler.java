package com.cs.ge.controllers.advice;

import com.cs.ge.entites.ExceptionData;
import com.cs.ge.exception.ApplicationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ExceptionData handleApplicationException(ApplicationException applicationException) {
        ExceptionData exceptionData = new ExceptionData();
        exceptionData.setTimestamp(LocalDateTime.now());
        exceptionData.setMessage(applicationException.getMessage());
        return exceptionData;
    }

    @ExceptionHandler( value = {ApplicationException.class})
    public ResponseEntity<Object> HandleException(ApplicationException e){
        HttpStatus badRequest= HttpStatus.BAD_REQUEST;
               ExceptionData exceptionData = new ExceptionData(
                e.getMessage(),
                e,
                badRequest,
                       LocalDateTime.now()
                );
        return new ResponseEntity<>(exceptionData, badRequest);
  }
}
