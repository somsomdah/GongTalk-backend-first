package com.dasom.gongtalk.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@JsonIgnoreProperties({"user", "post_content"})
@Table(name = "scrap", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "post_id"}))
public class Scrap extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Post post;

    public Scrap(User user, Post post) {
        this.user = user;
        this.post = post;
    }


}
