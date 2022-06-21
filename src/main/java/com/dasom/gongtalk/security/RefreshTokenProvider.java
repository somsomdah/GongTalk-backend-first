package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import com.dasom.gongtalk.domain.User;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenProvider extends TokenProvider {


    public RefreshTokenProvider(AppProperties appProperties, UserPrincipalService userDetailsService){
        super(appProperties, userDetailsService);
    }

    public String createTokenWithUser(User user) {
        return super.createTokenWithUserId(user.getId());
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