package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.Setting;
import com.dasom.gongtalk.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class UserInfoResponse {

    private Integer id;
    private String username;
    private String password;
    private String deviceNum;
    private Setting setting;

    public static UserInfoResponse fromUser(User user){
        return new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getDeviceNum(),
                user.getSetting());
    }

    public static List<UserInfoResponse> fromUsers(List<User> users){
        List<UserInfoResponse> response = new ArrayList<>();
        for (User user: users){
            response.add(UserInfoResponse.fromUser(user));
        }

        return response;
    }





}
