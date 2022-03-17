package com.dasom.gongtalk.exception;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Setter
public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue, String details){
        super(String.format("%s with %s %s not found", resourceName, fieldName, fieldValue), details);
    }
}
