package com.dasom.gongtalk.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String url;

    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Post post;

}
