package com.dasom.gongtalk.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Getter
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();

    @Getter
    @NoArgsConstructor
    public static class Auth{
        private String tokenSecret;
        private int tokenDurationMin;
    }

}
