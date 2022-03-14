package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.dto.TokenResponse;
import com.dasom.gongtalk.dto.UserLoginByDeviceRequest;
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

    @GetMapping("token/access")
    public ResponseEntity<TokenResponse> getAccessToken(@RequestHeader("Refresh-Token")String refreshToken){
        String accessToken = userService.getAccessToken(refreshToken);
        TokenResponse tokenResponse = new TokenResponse(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }

    @GetMapping("token/access")
    public ResponseEntity<TokenResponse> getRefreshToken(@RequestParam("deviceNum")String deviceNum){
        String refreshToken = userService.getRefreshToken(deviceNum);
        TokenResponse tokenResponse = new TokenResponse(refreshToken);
        return ResponseEntity.status(HttpStatus.OK).body(tokenResponse);
    }
}
