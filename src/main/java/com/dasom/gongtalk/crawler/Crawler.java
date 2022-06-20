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

        OldParser boardOldParser = new OldParser(info);
        List<String> postUrls = boardOldParser.extractPostUrls();
        for (String url : postUrls) {
            Post post = new Post(info.getBoard(), url);
            OldParser oldParser = new OldParser(post);

            try{
                post.setContent(oldParser.extractContent());
            }catch(Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - setContent%n",e.toString());
            }
            try{
                post.setTitle(oldParser.extractTitle());
            }catch(Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - setTitle%n",e.toString());
            }
            try{
                post.setWriter(oldParser.extractWriter());
            }catch(Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - setWriter%n",e.toString());
            }
            try{
                post.setDate(oldParser.extractDate(info.getPostDatePattern()));
            }catch(Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - setDate%n",e.toString());
            }
            try{
                post.setCategory(oldParser.extractCategory());
            }catch (Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - setCategory%n", e.toString());
            }
            try{
                postService.save(post);
                alarmService.save(post);
            }catch (Exception e){
                System.out.printf("[Exception] %s - in Crawler.crawl - save post and alarm%n", e.toString());
            }

        }

    }

//    @Scheduled(cron= CRON_EXPRESSION, zone= TIMEZONE)
    @Scheduled(fixedDelay=30*60*1000)
    public void run() throws IOException {
//        for (CrawlingInfo info : infoRepository.findAll()){
//            this.crawl(info);
//        }

    }
}
