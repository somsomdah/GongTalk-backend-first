package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.repository.BoardRepository;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Run {

    private final BoardRepository boardRepository;
    private final PostService postService;
    private final static int SCHEDULE_INTERVAL_MIN = 20;

    @Scheduled(fixedDelay = SCHEDULE_INTERVAL_MIN * 1000 * 60)
    public void run(){
        for (Board board : boardRepository.findAll()){
            Crawler.crawl(board, postService, boardRepository);
        }
    }


}
