package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.exception.UserForbiddenException;
import com.dasom.gongtalk.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board getFromId(Integer id){
        Optional<Board> board = boardRepository.findById(id);
        try{
            return board.get();
        }catch (Exception e){
            throw new ResourceNotFoundException(e.toString(), "board", "id", id);
        }
    }

    public void checkAuthority(User user, Board board){
        if(!user.getBoards().contains(board)){
            throw new UserForbiddenException(String.format("The user has no authority to board id %d", board.getId()));
        }
    }

}
