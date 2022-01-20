package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.school.School;
import com.dasom.gongtalk.domain.user.Subscribe;
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
    private List<String> keywords;
    private boolean notifyAll;

    public static List<BoardInfoResponse> fromUser(User user, SubscribeRepository subscribeRepository){
        List<Board> boards = user.getBoards();
        List<BoardInfoResponse> response = new ArrayList<>();
        
        
        for (Board board : boards){

            List<String> keywords = subscribeRepository.findAllKeywordContentByUserAndBoard(user, board);
            boolean notifyAll = subscribeRepository.existsByUserAndBoardAndType(user, board, "B");

            response.add(new BoardInfoResponse(
                    board.getId(), 
                    board.getName(), 
                    board.getSchool(),
                    keywords,
                    notifyAll
            ));
        }
        
        return response;
    }


}
