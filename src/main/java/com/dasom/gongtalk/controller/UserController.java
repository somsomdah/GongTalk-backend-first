package com.dasom.gongtalk.controller;

import com.dasom.gongtalk.domain.*;
import com.dasom.gongtalk.dto.*;
import com.dasom.gongtalk.repository.*;
import com.dasom.gongtalk.security.UserPrincipal;
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
import java.util.List;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final BoardService boardService;
    private final SubscribeService subscribeService;
    private final SubscribeRepository subscribeRepository;
    private final ScrapService scrapService;
    private final ScrapRepository scrapRepository;
    private final PostService postService;
    private final AlarmRepository alarmRepository;
    private final AlarmService alarmService;
    private final SettingService settingService;
    private final UserBoardRepository userBoardRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserCreateRequest request) {
        User user = userService.save(request.getDeviceNum());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserPrincipal userPrincipal){
        User user = userService.getFromPrincipal(userPrincipal);
        userRepository.delete(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("me")
    public ResponseEntity<UserInfoResponse> getMyInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getFromPrincipal(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(UserInfoResponse.fromUser(user));
    }

    @GetMapping("boards")
    public ResponseEntity<List<BoardInfoResponse>> getBoards(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getFromPrincipal(userPrincipal);
        List<Board> boards = userService.getBoards(user);
        List<BoardInfoResponse> response = BoardInfoResponse.fromBoards(boards);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping("boards/{boardId}")
    public ResponseEntity<UserBoard> addBoard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                              @PathVariable Integer boardId) {
        User user = userService.getFromPrincipal(userPrincipal);
        Board board = boardService.getFromId(boardId);
        UserBoard response = userBoardRepository.save(new UserBoard(user, board));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("boards/{boardId}")
    public ResponseEntity<?> updateBoardOrder(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                            @PathVariable Integer boardId,
                                                            @RequestBody UserBoardPatchRequest userBoardPatchRequest
    ) {
        User user = userService.getFromPrincipal(userPrincipal);
        UserBoard userBoard = userBoardRepository.findByUserAndBoardId(user, boardId);
        userBoard.setOrderValue(userBoardPatchRequest.getOrderValue());
        userBoardRepository.save(userBoard);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("boards/{boardId}")
    public ResponseEntity<?> deleteBoard(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @PathVariable Integer boardId) {
        User user = userService.getFromPrincipal(userPrincipal);
        Board board = boardService.getFromId(boardId);
        userService.deleteUserBoard(user, board);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "posts")
    public ResponseEntity<List<PostListResponse>> getPosts(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                           @RequestParam(required=false, defaultValue="100") int size) {
        User user = userService.getFromPrincipal(userPrincipal);
        List<Post> posts = userService.getPosts(user, size);
        List<PostListResponse> response = PostListResponse.fromPosts(posts);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("keywords/common")
    public ResponseEntity<List<KeywordResponse>> getCommonKeywords(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getFromPrincipal(userPrincipal);
        List<Keyword> keywords = userService.getCommonKeywords(user);
        List<KeywordResponse> response = KeywordResponse.fromKeywords(keywords);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("subscribes")
    public ResponseEntity<List<Subscribe>> getSubscribeInfo(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                            @RequestParam(required = false) Integer boardId,
                                                            @RequestParam(required = false) Subscribe.Type type
    ) {
        User user = userService.getFromPrincipal(userPrincipal);
        List<Subscribe> subscribes = subscribeRepository.findAllByUserAndTypeAndBoardId(user,type,boardId);
        return ResponseEntity.status(HttpStatus.OK).body(subscribes);
    }

    @PostMapping("subscribes")
    public ResponseEntity<Subscribe> createSubscribe(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                     @RequestBody SubscribeRequest subscribeRequest) {
        User user = userService.getFromPrincipal(userPrincipal);
        Subscribe subscribe = subscribeService.save(user, subscribeRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(subscribe);
    }

    @DeleteMapping("subscribes")
    public ResponseEntity<?> deleteSubscribe(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestBody SubscribeDeleteRequest subscribeDeleteRequest) {
        User user = userService.getFromPrincipal(userPrincipal);
        subscribeService.deleteSubscribe(user, subscribeDeleteRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "scraps")
    public ResponseEntity<List<Scrap>> getScrapedPosts(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                       @RequestParam(required = false, defaultValue = "0") int page,
                                                       @RequestParam(required = false, defaultValue = "100") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("date"));
        User user = userService.getFromPrincipal(userPrincipal);
        List<Scrap> scraps = scrapRepository.getAllByUser(user, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(scraps);
    }

    @PostMapping("scraps")
    public ResponseEntity<Scrap> createScrap(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @RequestBody PostIdRequest request) {
        User user = userService.getFromPrincipal(userPrincipal);
        Post post = postService.getFromId(request.getPostId());
        Scrap scrap = scrapService.save(user, post);
        return ResponseEntity.status(HttpStatus.CREATED).body(scrap);
    }

    @DeleteMapping("scraps/{scrapId}")
    public ResponseEntity<?> deleteScrap(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @PathVariable Integer scrapId) {
        User user = userService.getFromPrincipal(userPrincipal);
        Scrap scrap = scrapService.getFromId(scrapId);
        scrapService.checkAuthority(user, scrap);
        scrapRepository.deleteById(scrapId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping(value = "alarms")
    public ResponseEntity<List<Alarm>> getAlarms(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestParam(required = false, defaultValue = "0") int page,
                                                 @RequestParam(required = false, defaultValue = "100") int size) {
        User user = userService.getFromPrincipal(userPrincipal);
        Pageable pageable = PageRequest.of(page, size, Sort.by(new Sort.Order(Sort.Direction.DESC, "post.date")));
        List<Alarm> alarms = alarmRepository.findAllByUser(user, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(alarms);
    }

    @PatchMapping("alarms/{alarmId}")
    public ResponseEntity<Alarm> updateAlarm(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                             @PathVariable Integer alarmId, @RequestBody Alarm request) {
        User user = userService.getFromPrincipal(userPrincipal);
        Alarm oldAlarm = alarmService.getFromId(alarmId);
        alarmService.checkAuthority(user, oldAlarm);
        Alarm alarm = alarmService.update(alarmId, request);
        return ResponseEntity.status(HttpStatus.OK).body(alarm);
    }

    @GetMapping("setting")
    public ResponseEntity<Setting> getSetting(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userService.getFromPrincipal(userPrincipal);
        return ResponseEntity.status(HttpStatus.OK).body(user.getSetting());
    }

    @PatchMapping("setting")
    public ResponseEntity<Setting> updateSetting(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                                 @RequestBody Setting request) {
        User user = userService.getFromPrincipal(userPrincipal);
        Integer settingId = user.getSetting().getId();
        Setting setting = settingService.updateSetting(settingId, request);
        return ResponseEntity.status(HttpStatus.OK).body(setting);

    }

}
