package com.dasom.gongtalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@NoArgsConstructor
@Table(name = "board", uniqueConstraints = {@UniqueConstraint(columnNames = {"gubun", "name", "school_id"})})
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private String name;

    @NotNull
    private String shortName;

    private String gubun;

    @JoinColumn(name = "school_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private School school;

    @NotNull
    private String url;
    private String rowSelector;
    private String rowItemSelector;

    @NotNull
    private String postContentSelector;
    private String postTitleSelector;
    private String postCategorySelector;
    private String postWriterSelector;
    private String postDateSelector;
    private String postDatePattern;


    @Column(columnDefinition = "varchar(10) default 'GET'")
    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;

    public enum HttpMethod {
        GET,
        POST;
    }

}
