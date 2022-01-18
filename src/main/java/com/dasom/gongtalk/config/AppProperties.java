package com.dasom.gongtalk.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@Data
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();

    @Data
    @NoArgsConstructor
    public static class Auth{
        private String tokenSecret;
        private int tokenDurationMin;
    }


}
