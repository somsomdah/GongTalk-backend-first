package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String username);
    User findByDeviceNum(String deviceNum);
}
