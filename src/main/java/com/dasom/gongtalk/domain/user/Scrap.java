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
@JsonIgnoreProperties("user")
@Table(name="scrap", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
public class Scrap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Post post;

    public Scrap(User user, Post post){
        this.user = user;
        this.post = post;
    }


}
