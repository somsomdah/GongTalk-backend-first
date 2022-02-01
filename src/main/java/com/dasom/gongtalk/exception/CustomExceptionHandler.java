package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.dto.ExceptionResponse;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.exception.UserNotAuthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundExceptions(Exception ex, WebRequest req){
        ExceptionResponse response = new ExceptionResponse(ex.getMessage(), req.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
