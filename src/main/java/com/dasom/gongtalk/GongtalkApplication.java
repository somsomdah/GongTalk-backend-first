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

	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		System.out.println("========================== 자동배포 Test - 5 ==========================");
		SpringApplication.run(GongtalkApplication.class, args);
	}

}
