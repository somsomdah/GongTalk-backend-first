package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.Keyword;
import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, Integer> {

    Optional<Keyword> findByContent(String content);

    @Query("select s.keyword from Subscribe s where s.user=:user and s.type='CK'")
    List<Keyword> findAllCommonByUser(User user);

    @Query("select pk.keyword from PostKeyword pk where pk.post = :post")
    List<Keyword> findAllByPost(Post post);
}
