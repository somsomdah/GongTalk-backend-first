package com.dasom.gongtalk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserForbiddenException extends BaseException {
    public UserForbiddenException(String message, String details){
        super(message, details);
    }

    public UserForbiddenException(String message){
        super(message, message);
    }
}
