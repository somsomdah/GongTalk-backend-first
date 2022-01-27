package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.repository.BoardRepository;
import com.dasom.gongtalk.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Run {


    final BoardRepository boardRepository;
    final PostRepository postRepository;

    @Scheduled(fixedDelay = 1000)
    public void run(){
        for (Board board : boardRepository.findAll()){
            Crawler.crawl(board, postRepository);
        }
    }


}
