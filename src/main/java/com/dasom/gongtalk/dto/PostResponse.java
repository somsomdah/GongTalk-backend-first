package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class PostResponse {

    private Integer id;
    private Board board;
    private String url;
    private String title;
    private String writer;
    private String category;
    private String content;
    private LocalDate date;

    public static PostResponse fromPost(Post post) {
        return new PostResponse(post.getId(), post.getBoard(),
                post.getUrl(), post.getTitle(), post.getWriter(),
                post.getCategory(), post.getContent(), post.getDate());
    }
}
