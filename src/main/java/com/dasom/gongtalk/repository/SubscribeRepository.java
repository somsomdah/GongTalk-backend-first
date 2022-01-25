package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends CrudRepository<Subscribe, Integer> {

    List<Subscribe> findAllByTypeAndUser(String type, User user);
    List<Subscribe> findAllByUser(User user);
    List<Subscribe> findAllByUserAndBoard(User user, Board board);

    @Query("select s.keyword.content from Subscribe s where s.user=:user and s.board=:board and s.type='K'")
    List<String> findAllKeywordContentByUserAndBoard(User user, Board board);
    Boolean existsByUserAndBoardAndType(User user, Board board, String type);

}
