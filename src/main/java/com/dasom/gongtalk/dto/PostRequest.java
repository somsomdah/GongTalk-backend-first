package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;

import java.time.LocalDate;

public class PostRequest {

    private Board board;
    private Integer postNum;
    private String source;
    private String title;
    private String writer;
    private String category;
    private String content;
    private LocalDate date;
    private boolean isDeleted;
    private boolean isModified;
}
