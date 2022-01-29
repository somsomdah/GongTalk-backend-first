package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PostResponse {

    private Integer id;
    private Board board;
    private Integer postNum;
    private String title;
    private String writer;
    private String category;
    private String content;
    private LocalDate date;
    private boolean isDeleted;
    private boolean isModified;

    public static PostResponse fromPost(Post post){
        return new PostResponse(post.getId(), post.getBoard(),
                post.getPostNum(), post.getTitle(), post.getWriter(),
                post.getCategory(), post.getContent(), post.getDate(),
                post.isDeleted(), post.isModified());
    }

}
