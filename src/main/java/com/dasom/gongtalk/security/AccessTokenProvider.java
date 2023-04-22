package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenProvider extends TokenProvider {


    public AccessTokenProvider(AppProperties appProperties, UserPrincipalService userPrincipalService) {
        super(appProperties, userPrincipalService);
    }


    void setTokenDurationMin() {
        this.tokenDurationMin = this.appProperties.getAuth().getAccessTokenDurationMin();
    }

    void setTokenSecret() {
        this.tokenSecret = this.appProperties.getAuth().getAccessTokenSecret();
    }


}