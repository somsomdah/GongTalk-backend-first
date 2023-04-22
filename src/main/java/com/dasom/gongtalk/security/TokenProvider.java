package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import com.dasom.gongtalk.exception.UserNotAuthorizedException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public abstract class TokenProvider {

    protected final AppProperties appProperties;
    protected final UserPrincipalService userPrincipalService;

    protected int tokenDurationMin;
    protected String tokenSecret;

    public TokenProvider(AppProperties appProperties, UserPrincipalService userPrincipalService) {
        this.appProperties = appProperties;
        this.userPrincipalService = userPrincipalService;
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
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(tokenSecret)
                    .parseClaimsJws(token)
                    .getBody();
            return Integer.parseInt(claims.getSubject());
        } catch (Exception e) {
            throw new UserNotAuthorizedException("Invalid JWT", e.toString());
        }

    }

    public Authentication getAuthentication(String token) {
        UserPrincipal principal = this.userPrincipalService.loadUserPrincipalById(this.getUserIdFromToken(token));
        return new UserAuthentication(principal);
    }
}
