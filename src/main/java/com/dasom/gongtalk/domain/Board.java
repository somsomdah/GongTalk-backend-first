package com.dasom.gongtalk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(name="board", uniqueConstraints = {@UniqueConstraint(columnNames = {"gubun", "name", "school_id"})})
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String shortName;

    private String gubun;

    @JoinColumn(name = "school_id")
    @ManyToOne(fetch= FetchType.EAGER)
    private School school;

    @JsonIgnore
    @ToString.Exclude
    @OneToOne(mappedBy = "board", fetch = FetchType.LAZY)
    private CrawlingInfo crawlingInfo;

}
