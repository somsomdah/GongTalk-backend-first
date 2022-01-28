package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.Alarm;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, Integer> {

    List<Alarm> findAllByUserAndPostIn(User user, List<Post> posts);
}
