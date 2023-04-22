package com.dasom.gongtalk;

import com.dasom.gongtalk.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EnableConfigurationProperties(value = {AppProperties.class})
@EnableScheduling
@SpringBootApplication
public class GongtalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(GongtalkApplication.class, args);
    }

    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

}
