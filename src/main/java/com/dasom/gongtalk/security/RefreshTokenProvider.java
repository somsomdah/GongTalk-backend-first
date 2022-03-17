package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenProvider extends TokenProvider {


    public RefreshTokenProvider(AppProperties appProperties, CustomUserDetailsService userDetailsService){
        super(appProperties, userDetailsService);
    }

    @Override
    void setTokenDurationMin(){
        this.tokenDurationMin = this.appProperties.getAuth().getRefreshTokenDurationMin();
    }

    @Override
    void setTokenSecret(){
        this.tokenSecret = this.appProperties.getAuth().getRefreshTokenSecret();
    }


}