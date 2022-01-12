package com.dasom.gongtalk.domain.post;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@NoArgsConstructor
public class Pyo {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private String source;

}
