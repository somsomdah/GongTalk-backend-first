package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.keyword.Keyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KeywordRepository extends CrudRepository<Keyword, Integer> {

    Optional<Keyword> findByContent(String content);
}
