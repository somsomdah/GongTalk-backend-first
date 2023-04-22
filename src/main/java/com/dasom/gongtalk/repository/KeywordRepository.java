package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.Keyword;
import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Integer> {

    Optional<Keyword> findByContent(String content);

    @Query("select s.keyword from Subscribe s where s.user=:user and s.type='KEYWORD_COMMON'")
    List<Keyword> findAllCommonByUser(User user);

    @Query("select pk.keyword from PostKeyword pk where pk.post = :post")
    List<Keyword> findAllByPost(Post post);

    @Query("select k from Keyword k where k.isRecommended = true")
    List<Keyword> findAllRecommended();
}
