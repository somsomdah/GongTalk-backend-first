package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.UserBoard;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserBoardRepository extends CrudRepository<UserBoard, Integer> {

    @Query("select b.board from UserBoard b where b.user=:user")
    List<Board> findBoardsByUser(User user);
}

