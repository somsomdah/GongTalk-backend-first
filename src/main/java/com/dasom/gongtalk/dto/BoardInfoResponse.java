package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.School;
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
