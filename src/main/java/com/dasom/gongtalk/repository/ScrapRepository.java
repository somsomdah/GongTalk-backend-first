package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.user.Scrap;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends CrudRepository<Scrap, Integer> {

    List<Scrap> getAllByUser(User user, Pageable pageable);
}
