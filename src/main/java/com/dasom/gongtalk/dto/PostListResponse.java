package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class PostListResponse {

    private Integer id;
    private Board board;
    private String url;
    private String title;
    private String writer;
    private String category;
    private LocalDate date;

    public static List<PostListResponse> fromPosts(List<Post> posts) {

        List<PostListResponse> response = new ArrayList<>();
        for (Post post : posts) {
            if (response.stream().anyMatch(r -> r.getId().equals(post.getId()))) {
                continue;
            }
            PostListResponse resp = new PostListResponse(
                    post.getId(),
                    post.getBoard(),
                    post.getUrl(),
                    post.getTitle(),
                    post.getWriter(),
                    post.getCategory(),
                    post.getDate()
                    );

            response.add(resp);
        }

        return response;

    }


}
