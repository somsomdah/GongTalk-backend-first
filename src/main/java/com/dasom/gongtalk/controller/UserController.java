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

    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;

    private final UserService userService;

    @GetMapping public ResponseEntity<List<UserInfoResponse>> getAllUsers(){
        List<User> users = (List<User>) userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromEntities(users));
    }

    @GetMapping("{id}")
    public ResponseEntity<UserInfoResponse> getOneUser(@PathVariable Integer id){
        User user = userRepository.findById(id).get();
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
        User user = userRepository.findById(devicePrincipal.getId()).get();
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromEntity(user));
    }

    @GetMapping("boards")
    public ResponseEntity<List<Board>> getBoards(@AuthenticationPrincipal DevicePrincipal devicePrincipal){
        User user = userRepository.findById(devicePrincipal.getId()).get();
        List<Board> boards = userService.getBoards(user);
        return ResponseEntity.ok().body(boards);
    }

    @GetMapping(value = "subscribe", params = {"type"})
    public ResponseEntity<List<SubscribeInfoResponse>> getSubscribes(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                                               @RequestParam String type){
        User user = userRepository.findById(devicePrincipal.getId()).get();
        List<Subscribe> subscribes = subscribeRepository.findAllByTypeAndUser(type, user);
        List<SubscribeInfoResponse> response = SubscribeInfoResponse.fromEntities(subscribes);

        return ResponseEntity.ok().body(response);

    }

}
