package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class SubscribeInfoResponse {

    private String type;
    private User user;
    private Board board;
    private Keyword keyword;

    public static SubscribeInfoResponse fromEntity(Subscribe subscribe){
        return new SubscribeInfoResponse(
                subscribe.getType(),
                subscribe.getUser(),
                subscribe.getBoard(),
                subscribe.getKeyword());
    }

    public static List<SubscribeInfoResponse> fromEntities(List<Subscribe> subscribes){
        List<SubscribeInfoResponse> response = new ArrayList<>();
        for (Subscribe subscribe : subscribes){
            response.add(SubscribeInfoResponse.fromEntity(subscribe));
        }

        return response;
    }

}
