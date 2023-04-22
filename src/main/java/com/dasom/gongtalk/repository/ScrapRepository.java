package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.Scrap;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Integer> {

    List<Scrap> findAllByUser(User user, Pageable pageable);

    List<Scrap> findAllByUserAndPostId(User user, Integer postId);
}
