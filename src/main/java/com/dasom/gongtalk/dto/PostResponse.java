package com.dasom.gongtalk.dto;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.repository.PostRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
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

    public static List<PostResponse> fromUserEntity(User user, PostRepository postRepository){
        List<Board> boards = user.getBoards();
        List<Post> posts = postRepository.findByBoardIn(boards);
        List<PostResponse> response = new ArrayList<>();

        for(Post post: posts){
            response.add(new PostResponse(
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
                    post.isModified()
            ));
        }

        return response;
    }
}
