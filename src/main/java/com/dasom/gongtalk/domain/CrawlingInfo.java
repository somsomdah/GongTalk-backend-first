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
    @GeneratedValue
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


}
