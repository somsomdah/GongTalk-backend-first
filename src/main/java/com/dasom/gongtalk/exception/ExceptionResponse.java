package com.dasom.gongtalk.exception;

import lombok.Getter;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Date;
import java.util.HashMap;

@Getter
public class ExceptionResponse {

    private Date timestamp;
    private String message;
    private String details;

    public ExceptionResponse(String message, String details){
        this.timestamp = new Date();
        this.message = message;
        this.details = details;
    }

    public JSONObject toJson(){
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("timestamp", timestamp.toString());
        hashMap.put("message", this.message);
        hashMap.put("details", this.details);

        return new JSONObject(hashMap);
    }

}
