package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.Setting;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.repository.*;
import com.dasom.gongtalk.security.DevicePrincipal;
import com.dasom.gongtalk.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SettingRepository settingRepository;
    private final BoardRepository boardRepository;
    private final PostRepository postRepository;
    private final KeywordRepository keywordRepository;
    private final TokenProvider tokenProvider;

    public User getFromPrincipal(DevicePrincipal devicePrincipal){
        return getFromId(devicePrincipal.getId());
    }

    public User getFromId(Integer id){

        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()){
            throw new ResourceNotFoundException("User", "id", id);
        }
        return user.get();
    }

    public User save(String deviceNum){
        User newUser = new User(deviceNum);
        newUser.setSetting(settingRepository.save(new Setting()));
        return userRepository.save(newUser);
    }

    public void addUserBoard(User user, Board board){
        user.getBoards().add(board);
        userRepository.save(user);
    }

    public void deleteUserBoard(User user, Board board){
        user.getBoards().removeAll(Collections.singleton(board));
        userRepository.save(user);
    }

    public String getAuthToken(String deviceNum){
        User user = userRepository.findByDeviceNum(deviceNum);
        return tokenProvider.createToken(user);
    }

    public List<Board> getBoards(User user) {
        return boardRepository.findAllBoardsByUser(user);
    }

    public List<Post> getPosts(User user, int max){
        List<Board> boards = user.getBoards();
        Pageable limitMax= PageRequest.of(0,max, Sort.by("date"));
        return postRepository.findByBoardIn(boards, limitMax);
    }

    public List<Keyword> getCommonKeywords(User user){
        return keywordRepository.findAllCommonByUser(user);
    }


}
