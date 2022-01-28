package com.dasom.gongtalk.repository;

import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.Alarm;
import com.dasom.gongtalk.domain.user.Subscribe;
import com.dasom.gongtalk.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlarmRepository extends CrudRepository<Alarm, Integer> {

    List<Alarm> findAllByUser(User user);

    @Query(nativeQuery = true,value = (
            "select post.post_id as post_id, subscribe.user_id as user_id is_read" +
                    "from post, subscribe, post_keywords, alarm " +
                    "where (post.board_id = subscribe.board_id " +
                    "and post_keywords.post_id = post.id" +
                    "and post_keywords.keyword_id = subscribe.keyword_id" +
                    "and subscribe in :subscribes" +
                    "and subscribe.type=:type" +
                    "and alarm.is_read=false")
    )
    List<Alarm> findAllBySubscribe(Subscribe subscribe);
    List<Alarm> findAllByUserAndPost(User user, Post post);
}
