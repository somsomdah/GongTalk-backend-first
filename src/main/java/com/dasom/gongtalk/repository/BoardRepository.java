package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {


    @Query("select u.boards from User u where u=:user")
    List<Board> findAllBoardsByUser(User user);
}
