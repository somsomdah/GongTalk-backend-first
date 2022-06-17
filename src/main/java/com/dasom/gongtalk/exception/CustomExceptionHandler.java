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
    public final ResponseEntity<ExceptionResponse> handleResourceNotFoundExceptions(BaseException ex, WebRequest req){
        ExceptionResponse response = new ExceptionResponse(ex.toString(), req.toString());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(UserForbiddenException.class)
    public final ResponseEntity<ExceptionResponse> handleUserForbiddenException(BaseException ex, WebRequest req){
        ExceptionResponse response = new ExceptionResponse(ex.toString(), req.toString());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

    }

    @ExceptionHandler(SqlException.class)
    public final ResponseEntity<ExceptionResponse> handleSqlException(BaseException ex, WebRequest req){
        ExceptionResponse response = new ExceptionResponse(ex.toString(), req.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(UserNotAuthorizedException.class)
    public final ResponseEntity<ExceptionResponse> handleUserNotAuthorizedException(BaseException ex, WebRequest req){
        ExceptionResponse response = new ExceptionResponse(ex.toString(), req.toString());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
