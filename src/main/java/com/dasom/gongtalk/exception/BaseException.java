package com.dasom.gongtalk.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseException extends RuntimeException{

    private String details;

    public BaseException(String message, String details){
        super(message);
        setDetails(details);

        System.out.printf("[Exception] %s%n", message);
    }
}
