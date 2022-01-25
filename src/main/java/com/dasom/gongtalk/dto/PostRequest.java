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

    public Post toPost(){

        Post post = new Post();
        post.setPostNum(this.postNum);
        post.setSource(this.source);
        post.setTitle(this.title);
        post.setWriter(this.writer);
        post.setCategory(this.category);
        post.setContent(this.content);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate date = LocalDate.parse(this.date, formatter);
        post.setDate(date);

        return post;
    }
}
