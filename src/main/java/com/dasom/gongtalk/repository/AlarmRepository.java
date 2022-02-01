package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.user.Alarm;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, Integer> {

    List<Alarm> findAllByUser(User user, Pageable pageable);

}
