package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.dto.UserCreateRequest;
import com.dasom.gongtalk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("user")
    public ResponseEntity<User> createUser(@RequestBody UserCreateRequest userCreateRequest){
        User user = userService.saveUser(userCreateRequest.getDeviceNum());
        return ResponseEntity.created(URI.create(Integer.toString(user.getId()))).body(user);
    }

    @GetMapping("boards")
    public ResponseEntity<List<Board>> getBookmarkedBoards(@AuthenticationPrincipal User user){
        List<Board> boards = userService.getBookmarkedBoards(user);
        return ResponseEntity.ok().body(boards);
    }

}
