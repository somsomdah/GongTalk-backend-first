package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends CrudRepository<Subscribe, Integer> {

    List<Subscribe> findAllByUser(User user);

    List<Subscribe> findAllByUserAndType(User user, String type);

    List<Subscribe> findAllByUserAndTypeAndBoardAndKeyword(User user, String type, Board board, Keyword keyword);

    List<Subscribe> findAllByTypeAndBoardAndKeywordIn(String type, Board board, List<Keyword> keywords);

    List<Subscribe> findAllByTypeAndBoardAndKeyword(String type, Board board, Keyword keyword);



}
