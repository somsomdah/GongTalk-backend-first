package com.dasom.gongtalk.repository;


import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends CrudRepository<Subscribe, Integer> {

    List<Subscribe> findAllByTypeAndUser(String type, User user);

}
