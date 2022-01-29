package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.user.Alarm;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, Integer> {

}
