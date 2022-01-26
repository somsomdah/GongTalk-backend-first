package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.school.School;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.repository.SubscribeRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class BoardInfoResponse {

    private Integer id;
    private String name;
    private School school;

    public static List<BoardInfoResponse> fromBoards(List<Board> boards){
        List<BoardInfoResponse> response = new ArrayList<>();

        for (Board board : boards){


            response.add(new BoardInfoResponse(
                    board.getId(), 
                    board.getName(), 
                    board.getSchool()
            ));
        }
        
        return response;
    }


}
