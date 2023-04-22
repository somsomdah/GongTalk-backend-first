package com.dasom.gongtalk.exception;

import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Setter
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue, String details) {
        super(String.format("%s with %s [%s] not found", resourceName, fieldName, fieldValue), details);
    }
}
