package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.dto.ExceptionResponse;
import com.dasom.gongtalk.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundExceptions(BaseException ex){
        ExceptionResponse response = new ExceptionResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(SqlException.class)
    public final ResponseEntity<ExceptionResponse> handleBadRequestException(BaseException ex){
        ExceptionResponse response = new ExceptionResponse(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserForbiddenException.class)
    public final ResponseEntity<ExceptionResponse> handleUserForbiddenException(BaseException ex){
        ExceptionResponse response = new ExceptionResponse(ex);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotAuthorizedException(BaseException ex){
        ExceptionResponse response = new ExceptionResponse(ex);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
