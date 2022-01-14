package com.dasom.gongtalk.domain.post;

import lombok.Data;
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
    private String source;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Post post;

}
