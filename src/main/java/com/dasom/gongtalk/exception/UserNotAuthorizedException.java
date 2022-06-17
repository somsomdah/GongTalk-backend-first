package com.dasom.gongtalk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UserNotAuthorizedException extends BaseException {
    public UserNotAuthorizedException(String message){
        super(message);
    }
}
