package com.dasom.gongtalk.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseException extends RuntimeException{

    private String details;

    public BaseException(String message){
        super(message);
        setDetails(this.toString());

        System.out.printf("[Exception] %s%n", message);
    }
}
