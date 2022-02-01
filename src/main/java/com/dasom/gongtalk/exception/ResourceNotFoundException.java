package com.dasom.gongtalk.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message, String resourceName, String fieldName, Object fieldValue){
        super(String.format("%s - %s with %s %s not found", message, resourceName, fieldName, fieldValue));
    }
}
