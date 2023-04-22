package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.PostKeyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostKeywordRepository extends JpaRepository<PostKeyword, Integer> {

}
