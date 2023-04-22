package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.Alarm;
import com.dasom.gongtalk.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends JpaRepository<Alarm, Integer> {

    List<Alarm> findAllByUser(User user, Pageable pageable);

}
