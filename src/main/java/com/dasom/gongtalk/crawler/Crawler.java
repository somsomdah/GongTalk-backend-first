package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.CrawlingInfo;
import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.repository.CrawlingInfoRepository;
import com.dasom.gongtalk.service.AlarmService;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Crawler {

    private final PostService postService;
    private final AlarmService alarmService;
    private final CrawlingInfoRepository infoRepository;

    // TODO : 이 값들 property로 빼기
    private static final String TIMEZONE = "Asia/Seoul";
    private static final String CRON_EXPRESSION= "0 0 11,18 * * *";

    public void crawl(CrawlingInfo info) throws IOException {

        Parser boardParser = new Parser(info);
        List<String> postUrls = boardParser.extractPostUrls();

        for (String url : postUrls) {
            Post post = new Post(info.getBoard(), url);
            Parser parser = new Parser(post);

            try{
                post.setContent(parser.extractContent());
            }catch(Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - 1%n",e.toString());
            }
            try{
                post.setTitle(parser.extractTitle());
            }catch(Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - 2%n",e.toString());
            }
            try{
                post.setWriter(parser.extractWriter());
            }catch(Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - 3%n",e.toString());
            }
            try{
                post.setDate(parser.extractDate(info.getPostDatePattern()));
            }catch(Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - 4%n",e.toString());
            }
            try{
                post.setCategory(parser.extractCategory());
            }catch (Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - 5%n",e.toString());
            }
            try{
                postService.save(post);
                alarmService.save(post);
            }catch (Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - 2%n",e.toString());
            }

        }

    }

//    @Scheduled(cron= CRON_EXPRESSION, zone= TIMEZONE)
    @Scheduled(fixedDelay = 300*1000)
    public void run() throws IOException {
        for (CrawlingInfo info : infoRepository.findAll()){
            this.crawl(info);
        }
    }
}
