package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Keyword;
import com.dasom.gongtalk.domain.Subscribe;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {

    List<Subscribe> findAllByUser(User user);

    List<Subscribe> findAllByUserAndType(User user, String type);

    List<Subscribe> findAllByUserAndTypeAndBoardId(User user, Subscribe.Type type, Integer boardId);

    List<Subscribe> findAllByUserAndTypeAndBoardIdAndKeywordId(User user, Subscribe.Type type, Integer boardId, Integer keywordId);

    List<Subscribe> findAllByTypeAndBoardAndKeywordIn(Subscribe.Type type, Board board, List<Keyword> keywords);

    List<Subscribe> findAllByTypeAndBoardAndKeyword(Subscribe.Type type, Board board, Keyword keyword);


}
