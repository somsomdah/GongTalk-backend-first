package com.dasom.gongtalk.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(name="board", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "school_id"})})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @JoinColumn(name = "school_id")
    @ManyToOne(fetch= FetchType.EAGER)
    private School school;


    @OneToOne(mappedBy = "board", fetch = FetchType.LAZY)
    CrawlingInfo crawlingInfo;










}
