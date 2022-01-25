package com.dasom.gongtalk.domain.keyword;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String content;

    @NotNull
    @Column(columnDefinition="boolean default false")
    private boolean isDefault;
}
