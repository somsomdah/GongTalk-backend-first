package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.*;
import com.dasom.gongtalk.dto.*;
import com.dasom.gongtalk.exception.SqlException;
import com.dasom.gongtalk.repository.AlarmRepository;
import com.dasom.gongtalk.repository.ScrapRepository;
import com.dasom.gongtalk.repository.UserRepository;
import com.dasom.gongtalk.security.DevicePrincipal;
import com.dasom.gongtalk.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final BoardService boardService;
    private final SubscribeService subscribeService;
    private final ScrapRepository scrapRepository;
    private final PostService postService;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;
    private final SettingService settingService;
    private final ScrapService scrapService;

//    @GetMapping
//    public ResponseEntity<List<UserInfoResponse>> getAllUsers(){
//        List<User> users = (List<User>) userRepository.findAll();
//        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromUsers(users));
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<UserInfoResponse> getOneUser(@PathVariable Integer id){
//        User user = userService.getFromId(id);
//        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromUser(user));
//
//    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateRequest request){
        User user = userService.save(request.getDeviceNum());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginByDeviceRequest request){
        String token = userService.getAuthToken(request.getDeviceNum());
        UserLoginResponse response = UserLoginResponse.fromAuthTokenString(token);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("me")
    public ResponseEntity<UserInfoResponse> getMyInfo(@AuthenticationPrincipal DevicePrincipal devicePrincipal){
        User user = userService.getFromPrincipal(devicePrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromUser(user));
    }

    @GetMapping("boards")
    public ResponseEntity<List<BoardInfoResponse>> getBoards(@AuthenticationPrincipal DevicePrincipal devicePrincipal){
        User user = userService.getFromPrincipal(devicePrincipal);
        List<Board> boards = userService.getBoards(user);
        List<BoardInfoResponse> response = BoardInfoResponse.fromBoards(boards);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("boards/{boardId}")
    public ResponseEntity<UserBoardResponse> addBoard(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                                       @PathVariable Integer boardId){
        User user = userService.getFromPrincipal(devicePrincipal);
        Board board = boardService.getFromId(boardId);
        userService.addUserBoard(user,board);
        UserBoardResponse response = UserBoardResponse.fromUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("boards/{boardId}")
    public ResponseEntity<UserBoardResponse> deleteBoard(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                                         @PathVariable Integer boardId){
        User user = userService.getFromPrincipal(devicePrincipal);
        Board board = boardService.getFromId(boardId);
        boardService.checkAuthority(user, board);
        userService.deleteUserBoard(user,board);
        UserBoardResponse response = UserBoardResponse.fromUser(user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = "posts", params = {"size"})
    public ResponseEntity<List<PostListResponse>> getPosts(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                                           @RequestParam int size) {
        User user = userService.getFromPrincipal(devicePrincipal);
        List<Post> posts = userService.getPosts(user, size);
        List<PostListResponse> response = PostListResponse.fromPosts(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("keywords/common")
    public ResponseEntity<List<KeywordResponse>> getCommonKeywords(@AuthenticationPrincipal DevicePrincipal devicePrincipal){
        User user = userService.getFromPrincipal(devicePrincipal);
        List<Keyword> keywords = userService.getCommonKeywords(user);
        List<KeywordResponse> response = KeywordResponse.fromKeywords(keywords);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("subscribes")
    public  ResponseEntity<List<SubscribeInfoResponse>> getSubscribeInfo(@AuthenticationPrincipal DevicePrincipal devicePrincipal){
        User user = userService.getFromPrincipal(devicePrincipal);
        List<Subscribe> subscribes = subscribeService.getAllSubscribe(user);
        List<SubscribeInfoResponse> response = SubscribeInfoResponse.fromSubscribes(subscribes);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("subscribes")
    public ResponseEntity<Subscribe> createSubscribe(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                                                 @RequestBody SubscribeRequest subscribeRequest){
        User user = userService.getFromPrincipal(devicePrincipal);
        Subscribe subscribe = subscribeService.save(user, subscribeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscribe);
    }

    @DeleteMapping("subscribes")
    public ResponseEntity<?> deleteSubscribe(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                @RequestBody SubscribeDeleteRequest subscribeDeleteRequest){
        User user = userService.getFromPrincipal(devicePrincipal);
        subscribeService.deleteSubscribe(user, subscribeDeleteRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value="scraps")
    public ResponseEntity<List<Scrap>> getScrapedPosts(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                                                  @RequestParam(required = false, defaultValue ="0") int page,
                                                                  @RequestParam(required = false, defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("date"));
        User user = userService.getFromPrincipal(devicePrincipal);
        List<Scrap> scraps = scrapRepository.getAllByUser(user, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(scraps);
    }

    @PostMapping("scraps")
    public ResponseEntity<Scrap> createScrap(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                             @RequestBody PostIdRequest request){
        User user = userService.getFromPrincipal(devicePrincipal);
        Post post = postService.getFromId(request.getPostId());
        Scrap scrap = scrapService.save(user, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(scrap);
    }

    @DeleteMapping("scraps/{scrapId}")
    public ResponseEntity<?> deleteScrap(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                         @PathVariable Integer scrapId){
        User user = userService.getFromPrincipal(devicePrincipal);
        Scrap scrap = scrapService.getFromId(scrapId);
        scrapService.checkAuthority(user, scrap);
        scrapRepository.deleteById(scrapId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "alarms")
    public ResponseEntity<List<Alarm>> getAlarms(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                                 @RequestParam(required = false, defaultValue = "0") int page,
                                                 @RequestParam(required = false, defaultValue = "10") int size){
        User user = userService.getFromPrincipal(devicePrincipal);
        Pageable pageable = PageRequest.of(page, size, Sort.by(new Sort.Order(Sort.Direction.DESC,"post.date")));
        List<Alarm> alarms = alarmRepository.findAllByUser(user, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(alarms);
    }

    @PatchMapping("alarms/{alarmId}")
    public ResponseEntity<Alarm> updateAlarm(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                             @PathVariable Integer alarmId, @RequestBody Alarm request){
        User user = userService.getFromPrincipal(devicePrincipal);
        Alarm oldAlarm = alarmService.getFromId(alarmId);
        alarmService.checkAuthority(user, oldAlarm);
        Alarm alarm = alarmService.update(alarmId, request);
        return ResponseEntity.status(HttpStatus.OK).body(alarm);
    }

    @GetMapping("setting")
    public ResponseEntity<Setting> getSetting(@AuthenticationPrincipal DevicePrincipal devicePrincipal){
        User user = userService.getFromPrincipal(devicePrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(user.getSetting());
    }

    @PatchMapping("setting")
    public ResponseEntity<Setting> updateSetting(@AuthenticationPrincipal DevicePrincipal devicePrincipal,
                                                 @RequestBody Setting request){
        User user = userService.getFromPrincipal(devicePrincipal);
        Integer settingId = user.getSetting().getId();
        Setting setting = settingService.updateSetting(settingId, request);
        return ResponseEntity.status(HttpStatus.OK).body(setting);

    }

}
