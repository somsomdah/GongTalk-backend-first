package com.dasom.gongtalk.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SqlException extends BaseException {
    public SqlException(String message, String info){
        super(String.format("%s - %s",message, info));
    }

}
