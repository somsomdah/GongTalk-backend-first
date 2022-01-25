package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query("select p from Post p where p.board in :boards and p.isDeleted=false order by p.date desc")
    List<Post> findByBoardIn(List<Board> boards);

    @Query("select p from Post p join p.keywords k where k.content in :keywordsContent order by p.date desc ")
    List<Post> findAllByKeywordsContentIn(List<String> keywordsContent);
}
