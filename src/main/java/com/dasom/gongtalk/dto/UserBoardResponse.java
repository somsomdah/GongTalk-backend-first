package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserBoardResponse {
    Integer id;
    List<Board> boards;

    public static UserBoardResponse fromUser(User user){
        return new UserBoardResponse(user.getId(), user.getBoards());
    }


}
