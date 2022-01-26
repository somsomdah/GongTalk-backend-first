package com.dasom.gongtalk.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserLoginResponse {
    private String token;

    public static UserLoginResponse fromAuthTokenString(String token){
        return new UserLoginResponse(token);
    }
}
