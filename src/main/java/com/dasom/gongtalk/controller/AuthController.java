package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.dto.TokenResponse;
import com.dasom.gongtalk.dto.UserLoginByDeviceRequest;
import com.dasom.gongtalk.dto.IssueAccessTokenRequest;
import com.dasom.gongtalk.dto.UserLoginResponse;
import com.dasom.gongtalk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;


    @PostMapping("login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginByDeviceRequest request){
        String refreshToken = userService.getRefreshToken(request.getDeviceNum());
        String accessToken = userService.getAccessToken(refreshToken);
        UserLoginResponse response = new UserLoginResponse(refreshToken, accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("token/issue")
    public ResponseEntity<TokenResponse> getAccessToken(@RequestBody IssueAccessTokenRequest request){
        String accessToken = userService.getAccessToken(request.getRefreshToken());
        TokenResponse tokenResponse = new TokenResponse(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }

}
