package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.Board;
import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.service.AlarmService;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Crawler {

    // TODO : 이 값들 property로 빼기
    private static final String TIMEZONE = "Asia/Seoul";
    private static final String CRON_EXPRESSION = "0 0 11,18 * * *";
    private final PostService postService;
    private final AlarmService alarmService;

    public void crawl(Board board) throws IOException {

        Parser parser = new Parser(board);
        List<String> postUrls = parser.extractPostUrls();
        for (String url : postUrls) {
            Post post = Post.createPost(board, url);

            try {
                post.setContent(parser.extractContent());
            } catch (Exception e) {
                System.out.printf("[Exception] %s - in Crawler.crawl - setContent%n", e.toString());
            }
            try {
                post.setTitle(parser.extractTitle());
            } catch (Exception e) {
                System.out.printf("[Exception] %s - in Crawler.crawl - setTitle%n", e.toString());
            }
            try {
                post.setWriter(parser.extractWriter());
            } catch (Exception e) {
                System.out.printf("[Exception] %s - in Crawler.crawl - setWriter%n", e.toString());
            }
            try {
                post.setDate(parser.extractDate(board.getPostDatePattern()));
            } catch (Exception e) {
                System.out.printf("[Exception] %s - in Crawler.crawl - setDate%n", e.toString());
            }
            try {
                post.setCategory(parser.extractCategory());
            } catch (Exception e) {
                System.out.printf("[Exception] %s - in Crawler.crawl - setCategory%n", e.toString());
            }
            try {
                postService.save(post);
                alarmService.save(post);
            } catch (Exception e) {
                System.out.printf("[Exception] %s - in Crawler.crawl - save post and alarm%n", e.toString());
            }

        }

    }
//    @Scheduled(fixedDelay=30*60*1000)
//    public void run() throws IOException {
//        for (Board board : boardRepository.findAll()){
//            this.crawl(board);
//        }
//    }

}
