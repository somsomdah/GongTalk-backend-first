package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@AllArgsConstructor
public class PostResponse {

    private Integer id;
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

    public static PostResponse fromEntity(Post post){
        return new PostResponse(post.getId(), post.getBoard(),
                post.getPostNum(), post.getSource(), post.getTitle(),
                post.getWriter(), post.getCategory(), post.getContent(), post.getDate(),
                post.isDeleted(), post.isModified());
    }

}
