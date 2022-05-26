package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Subscribe;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends CrudRepository<Board, Integer> {


    @Query("select ub.user from UserBoard ub where ub.user=:user")
    List<Board> findAllBoardsByUser(User user);

    @Query("select s.board from Subscribe s where s in :subscribes")
    List<Board> findAllBySubscribe(List<Subscribe> subscribes);

    List<Board> findAllBySchoolId(Integer id, Pageable pageable);
}
