package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query("select p from Post p where p.board in :boards and p.isDeleted=false order by p.date desc")
    List<Post> findAllByBoardIn(List<Board> boards, Pageable pageable);

    List<Post> findAllByBoard(Board board, Pageable pageable);

    @Query("select pk.post from PostKeyword pk where pk.keyword.content in (:keywordsContent) order by pk.post.date desc")
    List<Post> findAllByKeywordsContentIn(List<String> keywordsContent);

    @Query("select pk.post from PostKeyword pk where pk.keyword.content in (:keywordsContent) and pk.post.board =:board order by pk.post.date desc")
    List<Post> findAllByBoardAndKeywordsContentIn(Board board,List<String> keywordsContent);


}
