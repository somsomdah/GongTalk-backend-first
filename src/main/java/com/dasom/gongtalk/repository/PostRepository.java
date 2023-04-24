package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p where p.board in :boards and p.deletedDate=null order by p.date desc")
    List<Post> findAllByBoardIn(List<Board> boards, Pageable pageable);

    List<Post> findAllByBoard(Board board, Pageable pageable);

    @Query("select pk.post from PostKeyword pk where pk.keyword.content in (:keywordsContent) order by pk.post.date desc")
    List<Post> findAllByKeywordsContentIn(List<String> keywordsContent);

    @Query("select pk.post from PostKeyword pk where pk.keyword.content in (:keywordsContent) and pk.post.board.id =:boardId order by pk.post.date desc")
    List<Post> findAllByBoardIdAndKeywordsContentIn(Long boardId, List<String> keywordsContent);

    List<Post> findAllByIdIn(List<Long> Ids);


}
