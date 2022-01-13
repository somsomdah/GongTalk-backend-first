package com.dasom.gongtalk.domain.member;

import com.dasom.gongtalk.domain.post.Post;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Data
@NoArgsConstructor
public class Alarm {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private Member member;
    @ManyToOne
    private Post post;

    private boolean isRead;
}
