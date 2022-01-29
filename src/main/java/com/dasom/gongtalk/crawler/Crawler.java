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

    private static final int MAX_NO_POST_COUNT = 20;

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
        Integer tempPostNum = board.getLastPostNum();
        Integer newPostNum = board.getLastPostNum();

        while(true){
            int noPostCount = 0;
            tempPostNum ++ ;
            try{
                Post post = createPost(board, tempPostNum);
                postService.save(post);
                alarmService.save(post);
                newPostNum = tempPostNum;

            }catch (Exception ex){
                System.out.println(200001);
                System.out.println(ex.toString());
                noPostCount ++;
                if (noPostCount > MAX_NO_POST_COUNT){
                    System.out.println(200001);
                    break;
                }
            }

        }

        board.setLastPostNum(newPostNum);
        System.out.println(222222);
        System.out.println(board.toString());
        System.out.println(222222);

        try{
            boardRepository.save(board);
        }catch (Exception ex){
            System.out.println(333333);
            System.out.println(ex);
            System.out.println(333333);
        }

    }

}
