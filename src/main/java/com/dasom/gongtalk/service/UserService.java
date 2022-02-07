package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.*;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.exception.SqlException;
import com.dasom.gongtalk.repository.*;
import com.dasom.gongtalk.security.DevicePrincipal;
import com.dasom.gongtalk.security.RefreshTokenProvider;
import com.dasom.gongtalk.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private final UserBoardRepository userBoardRepository;
    private final TokenProvider tokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    public User getFromPrincipal(DevicePrincipal devicePrincipal){
        return getFromId(devicePrincipal.getId());
    }

    public User getFromId(Integer id){

        Optional<User> user = userRepository.findById(id);
        try{
            return user.get();
        }catch (Exception e){
            throw new ResourceNotFoundException(e.toString(), "user", "id", id);
        }

    }

    public User save(String deviceNum){
        try {
            User newUser = new User(deviceNum);
            newUser.setSetting(settingRepository.save(new Setting()));
            return userRepository.save(newUser);
        }catch(Exception e){
            throw new SqlException(e.toString(), "User with this device number already exists");
        }

    }

    public void addUserBoard(User user, Board board){
        userBoardRepository.save(new UserBoard(user, board));
    }

    public void deleteUserBoard(User user, Board board){
        userBoardRepository.deleteAll(userBoardRepository.findAllByUserAndBoard(user, board));
    }

    public String getAccessToken(String refreshToken){
        Integer userId = refreshTokenProvider.getUserIdFromToken(refreshToken);
        return tokenProvider.createTokenWithUserId(userId);
    }

    public String getRefreshToken(String deviceNum){
        User user = userRepository.findByDeviceNum(deviceNum);
        return refreshTokenProvider.createToken(user);
    }

    public List<Board> getBoards(User user) {
        return boardRepository.findAllBoardsByUser(user);
    }

    public List<Post> getPosts(User user, int max){
        List<Board> boards = boardRepository.findAllBoardsByUser(user);
        Pageable limitMax= PageRequest.of(0,max, Sort.by("date"));
        return postRepository.findAllByBoardIn(boards, limitMax);
    }

    public List<Keyword> getCommonKeywords(User user){
        return keywordRepository.findAllCommonByUser(user);
    }


}
