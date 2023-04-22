package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Subscribe;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {


    @Query("select ub.board from UserBoard ub where ub.user=:user order by ub.orderValue")
    List<Board> findAllByUser(User user);

    @Query("select s.board from Subscribe s where s in :subscribes")
    List<Board> findAllBySubscribe(List<Subscribe> subscribes);

    List<Board> findAllBySchoolId(Integer id, Pageable pageable);
}
