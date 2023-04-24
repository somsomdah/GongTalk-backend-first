package com.dasom.gongtalk.service;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.User;
import com.dasom.gongtalk.exception.ResourceNotFoundException;
import com.dasom.gongtalk.exception.UserForbiddenException;
import com.dasom.gongtalk.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public Board getFromId(Long id) {
        Optional<Board> board = boardRepository.findById(id);
        try {
            return board.get();
        } catch (Exception e) {
            throw new ResourceNotFoundException("board", "id", id, e.toString());
        }
    }

    public List<Board> getAllFromSchoolId(Long schoolId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
        return boardRepository.findAllBySchoolId(schoolId, pageable);
    }

    public void checkAuthority(User user, Board board) {
        if (boardRepository.findAllByUser(user).contains(board)) {
            throw new UserForbiddenException(String.format("The user has no authority to board id %d", board.getId()));
        }
    }

}
