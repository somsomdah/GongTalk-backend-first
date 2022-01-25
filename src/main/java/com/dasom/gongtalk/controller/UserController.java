package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.dto.*;
import com.dasom.gongtalk.repository.SubscribeRepository;
import com.dasom.gongtalk.repository.UserRepository;
import com.dasom.gongtalk.security.DevicePrincipal;
import com.dasom.gongtalk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<UserInfoResponse>> getAllUsers(){
        List<User> users = (List<User>) userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromEntities(users));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserInfoResponse> getOneUser(@PathVariable Integer id){
        User user = userService.getFromId(id);
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromEntity(user));

    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateRequest request){
        User user = userService.save(request.getDeviceNum());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginByDeviceRequest request){
        UserLoginResponse response = userService.login(request.getDeviceNum());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("me")
    public ResponseEntity<UserInfoResponse> getMyInfo(@AuthenticationPrincipal DevicePrincipal devicePrincipal){
        User user = userService.getFromPrincipal(devicePrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromEntity(user));
    }

    @GetMapping("boards")
    public ResponseEntity<List<BoardInfoResponse>> getBoards(@AuthenticationPrincipal DevicePrincipal devicePrincipal){
        User user = userService.getFromPrincipal(devicePrincipal);
        List<BoardInfoResponse> response = userService.getBoards(user);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("posts")
    public ResponseEntity<List<PostFromUserResponse>> getPosts(@AuthenticationPrincipal DevicePrincipal devicePrincipal) {
        User user = userService.getFromPrincipal(devicePrincipal);
        List<PostFromUserResponse> response = userService.getPosts(user);
        return ResponseEntity.ok().body(response);
    }

}
