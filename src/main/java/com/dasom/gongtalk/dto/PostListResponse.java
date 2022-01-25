package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@Getter
public class PostListResponse {

    private Integer id;
    private Board board;
    private Integer postNum;
    private String source;
    private String title;
    private String writer;
    private String category;
    private LocalDate date;
    private boolean isDeleted;
    private boolean isModified;

    public static List<PostListResponse> fromPosts(List<Post> posts){

        List<PostListResponse> response = new ArrayList<>();
        for(Post post: (Collection<Post>) posts){
            if (response.stream().anyMatch(r ->r.getId().equals(post.getId()))) {
                continue;
            }
            PostListResponse resp = new PostListResponse(
                    post.getId(),
                    post.getBoard(),
                    post.getPostNum(),
                    post.getSource(),
                    post.getTitle(),
                    post.getWriter(),
                    post.getCategory(),
                    post.getDate(),
                    post.isDeleted(),
                    post.isModified());

            response.add(resp);
        }

        return response;

    }


}
