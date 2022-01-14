package com.dasom.gongtalk;

import com.dasom.gongtalk.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(value = {AppProperties.class})
@SpringBootApplication
public class GongtalkApplication {

	public static void main(String[] args) {
		SpringApplication.run(GongtalkApplication.class, args);
	}

}
