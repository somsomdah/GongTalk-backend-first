package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import com.dasom.gongtalk.domain.User;
import com.dasom.gongtalk.exception.UserNotAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public abstract class TokenProvider {

    protected final AppProperties appProperties;
    protected final CustomUserDetailsService userDetailsService;

    protected int tokenDurationMin;
    protected String tokenSecret;

    public TokenProvider(AppProperties appProperties, CustomUserDetailsService userDetailsService) {
        this.appProperties = appProperties;
        this.userDetailsService = userDetailsService;
        setTokenDurationMin();
        setTokenSecret();
    }

    abstract void setTokenDurationMin();
    abstract void setTokenSecret();

    public String createTokenWithUserId(Integer userId) {
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + Duration.ofMinutes(tokenDurationMin).toMillis());

        return Jwts.builder()
                .setSubject(Integer.toString(userId))
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();
    }

    public Integer getUserIdFromToken(String token) {
        try{
            Claims claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.parseInt(claims.getSubject());
        }catch (Exception e){
            throw new UserNotAuthorizedException("Invalid JWT");
        }


    }
}
