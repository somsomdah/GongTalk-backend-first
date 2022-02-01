package com.dasom.gongtalk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundExceptions(Exception ex, WebRequest req){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), req.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserForbiddenException.class)
    public final ResponseEntity<ExceptionResponse> handleUserForbiddenException(Exception ex, WebRequest req){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), req.getDescription(true));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

    }
}
