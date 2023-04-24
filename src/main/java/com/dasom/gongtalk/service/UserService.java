package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.*;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.exception.SqlException;
import com.dasom.gongtalk.repository.*;
import com.dasom.gongtalk.security.AccessTokenProvider;
import com.dasom.gongtalk.security.RefreshTokenProvider;
import com.dasom.gongtalk.security.UserPrincipal;
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
    private final AccessTokenProvider accessTokenProvider;
    private final RefreshTokenProvider refreshTokenProvider;

    public User getFromPrincipal(UserPrincipal userPrincipal) {
        return getFromId(userPrincipal.getId());
    }

    public User getFromId(Long id) {

        Optional<User> user = userRepository.findById(id);
        try {
            return user.get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("user", "id", id, e.toString());
        }

    }

    public User getFromDeviceNum(String deviceNum) {

        Optional<User> user = userRepository.findByDeviceNum(deviceNum);
        try {
            return user.get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("user", "deviceNum", deviceNum, e.toString());
        }

    }

    public User getFromUsername(String username) {

        Optional<User> user = userRepository.findByUsername(username);
        try {
            return user.get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("user", "username", username, e.toString());
        }

    }

    public User save(String deviceNum) {
        try {
            User newUser = new User(deviceNum);
            newUser.setSetting(settingRepository.save(new Setting()));
            return userRepository.save(newUser);
        } catch (Exception e) {
            System.out.println("=================");
            System.out.println(e);
            System.out.println("=================");
            throw new SqlException("User create error", e.toString());
        }

    }

    public void addUserBoard(User user, Board board) {
        userBoardRepository.save(new UserBoard(user, board));
    }

    public void deleteUserBoard(User user, Board board) {
        userBoardRepository.deleteAll(userBoardRepository.findAllByUserAndBoard(user, board));
    }

    public String getAccessToken(String refreshToken) {
        Long userId = refreshTokenProvider.getUserIdFromToken(refreshToken);
        return accessTokenProvider.createTokenWithUserId(userId);
    }

    public String getRefreshToken(String deviceNum) {
        User user = this.getFromDeviceNum(deviceNum);
        return refreshTokenProvider.createTokenWithUser(user);
    }

    public List<Board> getBoards(User user) {
        return boardRepository.findAllByUser(user);
    }

    public List<Post> getPosts(User user, int max) {
        List<Board> boards = boardRepository.findAllByUser(user);
        Pageable limitMax = PageRequest.of(0, max, Sort.by(Sort.Direction.DESC, "date"));
        return postRepository.findAllByBoardIn(boards, limitMax);
    }

    public List<Keyword> getCommonKeywords(User user) {
        return keywordRepository.findAllCommonByUser(user);
    }


}
