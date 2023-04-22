package com.dasom.gongtalk.config;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Getter
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();

    @Data
    @NoArgsConstructor
    public static class Auth {
        private String accessTokenSecret;
        private int accessTokenDurationMin;
        private String refreshTokenSecret;
        private int refreshTokenDurationMin;
    }

}
