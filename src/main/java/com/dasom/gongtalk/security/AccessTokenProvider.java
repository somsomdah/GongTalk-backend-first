package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AccessTokenProvider extends TokenProvider {


    public AccessTokenProvider(AppProperties appProperties, CustomUserDetailsService userDetailsService) {
        super(appProperties, userDetailsService);
    }


    void setTokenDurationMin() {
        this.tokenDurationMin = this.appProperties.getAuth().getAccessTokenDurationMin();
    }

    void setTokenSecret() {
        this.tokenSecret = this.appProperties.getAuth().getAccessTokenSecret();
    }


    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserById(this.getUserIdFromToken(token));
        return new DeviceNumAuthenticationToken(userDetails, userDetails.getAuthorities());
    }




}