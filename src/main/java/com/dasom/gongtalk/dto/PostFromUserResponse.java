package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.keyword.Keyword;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostFromUserResponse {

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
    private List<Keyword> keywords;

    public static List<PostFromUserResponse> fromUserEntity(User user, PostRepository postRepository){
        List<Board> boards = user.getBoards();
        List<Post> posts = postRepository.findByBoardIn(boards);
        List<PostFromUserResponse> response = new ArrayList<>();

        for(Post post: posts){
            response.add(new PostFromUserResponse(
                    post.getId(),
                    post.getBoard(),
                    post.getPostNum(),
                    post.getSource(),
                    post.getTitle(),
                    post.getWriter(),
                    post.getCategory(),
                    post.getContent(),
                    post.getDate(),
                    post.isDeleted(),
                    post.isModified(),
                    null
            ));
        }

        return response;
    }
}
