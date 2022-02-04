package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Keyword;
import com.dasom.gongtalk.domain.Subscribe;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends CrudRepository<Subscribe, Integer> {

    List<Subscribe> findAllByUser(User user);

    List<Subscribe> findAllByUserAndType(User user, String type);

    List<Subscribe> findAllByUserAndTypeAndBoardAndKeyword(User user, Subscribe.Type type, Board board, Keyword keyword);

    List<Subscribe> findAllByTypeAndBoardAndKeywordIn(Subscribe.Type type, Board board, List<Keyword> keywords);

    List<Subscribe> findAllByTypeAndBoardAndKeyword(Subscribe.Type type, Board board, Keyword keyword);



}
