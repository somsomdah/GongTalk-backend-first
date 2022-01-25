package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
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
        if (board.isEmpty()){
            throw new ResourceNotFoundException("Board", "id", id);
        }
        return board.get();
    }

}
