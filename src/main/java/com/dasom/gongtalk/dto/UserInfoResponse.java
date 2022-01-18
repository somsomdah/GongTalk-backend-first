package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.user.Setting;
import com.dasom.gongtalk.domain.user.User;
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

    public static UserInfoResponse fromEntity(User user){
        return new UserInfoResponse(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getDeviceNum(),
                user.getSetting());
    }

    public static List<UserInfoResponse> fromEntities(List<User> users){
        List<UserInfoResponse> response = new ArrayList<>();
        for (User user: users){
            response.add(UserInfoResponse.fromEntity(user));
        }

        return response;
    }





}
