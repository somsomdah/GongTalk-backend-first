package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.PostKeyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostKeywordRepository extends CrudRepository<PostKeyword, Integer> {

}
