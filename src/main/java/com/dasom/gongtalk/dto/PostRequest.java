package com.dasom.gongtalk.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostRequest {

    private Long boardId;
    private Integer postNum;
    private String source;
    private String title;
    private String writer;
    private String category;
    private String content;
    private String date;

}
