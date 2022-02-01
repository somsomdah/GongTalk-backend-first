package com.dasom.gongtalk.domain.user;

import com.dasom.gongtalk.domain.post.Post;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"user"})
@Table(name="alarm", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @NotNull
    private User user;

    @ManyToOne
    @NotNull
    private Post post;

    private boolean isRead;

    public Alarm(User user, Post post){
        this.user = user;
        this.post = post;
        this.isRead = false;
    }
}
