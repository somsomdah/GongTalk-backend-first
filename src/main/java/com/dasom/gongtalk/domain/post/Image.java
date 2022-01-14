package com.dasom.gongtalk.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String source;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Post post;

}
