package com.dasom.gongtalk.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Data
@Entity
public class CrawlingInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JoinColumn(name="board_id")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    Board board;

    @NotNull
    private String boardUrl;
    private String boardRowSelector;
    private String boardRowItemSelector;
    private String boardRowItemAttr;

    @NotNull
    private String postBaseUrl;
    private String postContentSelector;
    private String postTitleSelector;
    private String postCategorySelector;
    private String postWriterSelector;
    private String postDateSelector;
    private String postDatePattern;

    @NotNull
    private String imageBaseUrl;
    private String imageRowSelector;
    private String imageRowItemSelector;
    private String imageRowItemAttr;

    @NotNull
    private String fileBaseUrl;
    private String fileRowSelector;
    private String fileRowItemSelector;
    private String fileRowItemAttr;

    @Column(columnDefinition = "varchar(10) default 'POST'")
    @Enumerated(EnumType.STRING)
    private HttpMethod httpMethod;

    public enum HttpMethod {
        POST,
        GET;
    }


}
