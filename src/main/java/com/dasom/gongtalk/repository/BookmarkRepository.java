package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.user.Bookmark;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends CrudRepository<Bookmark, Integer> {

    @Query("select b.board from Bookmark b where b.user=?1")
    List<Board> findBoardsByUser(User user);
}

