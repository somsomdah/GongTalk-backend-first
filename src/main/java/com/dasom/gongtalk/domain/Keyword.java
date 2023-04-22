package com.dasom.gongtalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Keyword extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    @Column(unique = true)
    private String content;

    @NotNull
    private boolean isRecommended;

    public Keyword(String content) {
        this.content = content;
        this.isRecommended = false;
    }

}
