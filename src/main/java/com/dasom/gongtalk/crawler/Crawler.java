package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.domain.post.Post;
import com.dasom.gongtalk.repository.BoardRepository;
import com.dasom.gongtalk.service.AlarmService;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class Crawler {

    private final PostService postService;
    private final BoardRepository boardRepository;
    private final AlarmService alarmService;

    private static final int MAX_NO_POST_COUNT = 10;

    public Post createPost(Board board, Integer postNum) throws IOException {

        Post post = new Post(board, postNum);

        String pageUrl = post.getUrl();

        Parser parser = new Parser(board, pageUrl);
        post.setContent(parser.extractContent());
        post.setTitle(parser.extractTitle());
        post.setWriter(parser.extractWriter());
        post.setDate(parser.extractDate(board.getDatePattern()));

        try{
            post.setCategory(parser.extractCategory());
        }catch (Exception ignored){
        }

        return post;
    }



    public void crawl(Board board){
        Integer newPostNum = board.getLastPostNum();
        int noPostCount = 0;

        while(true){
            newPostNum ++ ;
            try{
                Post post = createPost(board, newPostNum);
                postService.save(post);
                alarmService.save(post);
                board.setLastPostNum(newPostNum);
                boardRepository.save(board);
            }catch (Exception ex){
                System.out.println("[Exception] Crawler : crawl : "+ex.toString());
                noPostCount ++;
                if (noPostCount > MAX_NO_POST_COUNT){
                    break;
                }
            }

        }

    }

}
