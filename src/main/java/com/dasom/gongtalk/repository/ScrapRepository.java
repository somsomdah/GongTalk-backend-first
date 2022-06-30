package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.domain.Scrap;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends CrudRepository<Scrap, Integer> {

    List<Scrap> findAllByUser(User user, Pageable pageable);
    List<Scrap> findAllByUserAndPostId(User user, Integer postId);
}
