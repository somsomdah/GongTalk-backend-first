package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.User;
import com.dasom.gongtalk.domain.UserBoard;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBoardRepository extends CrudRepository<UserBoard, Integer> {

    List<UserBoard> findAllByUserAndBoard(User user, Board board);
}
