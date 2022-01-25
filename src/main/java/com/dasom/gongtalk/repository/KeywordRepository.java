package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, Integer> {

    Optional<Keyword> findByContent(String content);
}
