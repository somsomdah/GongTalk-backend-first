package com.dasom.gongtalk.exception;

public class BaseException extends RuntimeException{

    public BaseException(String message){
        super(message);
        System.out.printf("[Exception] %s%n", message);
    }
}
