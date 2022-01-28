package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.board.Board;
import com.dasom.gongtalk.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class Run {

    private final static int SCHEDULE_INTERVAL_MIN = 20;
    private final BoardRepository boardRepository;
    private final Crawler crawler = new Crawler();

    @Scheduled(fixedDelay = SCHEDULE_INTERVAL_MIN * 1000 * 60)
    public void run(){
        for (Board board : boardRepository.findAll()){
            crawler.crawl(board);
        }
    }


}
