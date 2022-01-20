package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.Setting;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.dto.BoardInfoResponse;
import com.dasom.gongtalk.dto.UserLoginResponse;
import com.dasom.gongtalk.repository.SettingRepository;
import com.dasom.gongtalk.repository.SubscribeRepository;
import com.dasom.gongtalk.repository.UserRepository;
import com.dasom.gongtalk.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SettingRepository settingRepository;
    private final SubscribeRepository subscribeRepository;
    private final TokenProvider tokenProvider;

    public User save(String deviceNum){
        User newUser = new User(deviceNum);
        newUser.setSetting(settingRepository.save(new Setting()));
        return userRepository.save(newUser);
    }

    public UserLoginResponse login(String deviceNum){
        User user = userRepository.findByDeviceNum(deviceNum);
        String token = tokenProvider.createToken(user);
        return new UserLoginResponse(token);
    }

    public List<BoardInfoResponse> getBoards(User user) {
        return BoardInfoResponse.fromUser(user, subscribeRepository);
    }


}
