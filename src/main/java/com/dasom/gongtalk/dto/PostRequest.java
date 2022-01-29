package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
public class PostRequest {

    private Integer boardId;
    private Integer postNum;
    private String source;
    private String title;
    private String writer;
    private String category;
    private String content;
    private String date;

}
