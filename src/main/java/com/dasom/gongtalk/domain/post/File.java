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
    @GeneratedValue
    private int id;

    @NotNull
    private String source;

    @ManyToOne(fetch = FetchType.EAGER)
    @NotNull
    private Post post;

}
