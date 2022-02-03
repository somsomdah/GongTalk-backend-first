package com.dasom.gongtalk.crawler;

import com.dasom.gongtalk.domain.CrawlingInfo;
import com.dasom.gongtalk.domain.Post;
import com.dasom.gongtalk.exception.SqlException;
import com.dasom.gongtalk.repository.CrawlingInfoRepository;
import com.dasom.gongtalk.service.AlarmService;
import com.dasom.gongtalk.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
//@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "app.crawling")
public class Crawler {

    private final PostService postService;
    private final AlarmService alarmService;
    private final CrawlingInfoRepository infoRepository;

    private static final String TIMEZONE = "Asia/Seoul";
//    private static final String CRON_EXPRESSION= "0 0 11,18 * * *";
    private static final String CRON_EXPRESSION= "* 1 * * * *";

    public void crawl(CrawlingInfo info) throws IOException {

        Parser boardParser = new Parser(info);
        List<String> postUrls = boardParser.extractPostUrls();

        for (String url : postUrls) {

            Post post = new Post(info.getBoard(), url);
            Parser parser = new Parser(post);

            post.setContent(parser.extractContent());
            post.setTitle(parser.extractTitle());
            post.setWriter(parser.extractWriter());
            post.setDate(parser.extractDate(info.getPostDatePattern()));
            try{
                post.setCategory(parser.extractCategory());
            }catch (Exception ignored){}

            try{
                postService.save(post);
                alarmService.save(post);
            }catch (Exception e){
                throw new SqlException(e.toString(), "Exception in Crawling");
            }

        }

    }

    @Scheduled(cron= CRON_EXPRESSION, zone=TIMEZONE)
    public void run() throws IOException {
        for (CrawlingInfo info : infoRepository.findAll()){
            this.crawl(info);
        }
    }
}
