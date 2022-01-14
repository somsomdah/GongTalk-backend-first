package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.domain.user.User;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@Slf4j
public class TokenProvider {

    private final AppProperties appProperties;


    public TokenProvider(AppProperties appProperties) {
        this.appProperties = appProperties;
    }

    public String createToken(User user){
        Date now = new Date();
        Date expirationTime =new Date(now.getTime()+ Duration.ofMinutes(30).toMillis());
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, appProperties.getAuth().getTokenSecret())
                .compact();
    }


    public Integer getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(appProperties.getAuth().getTokenSecret())
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token);
            return true;
        }catch(Exception ex){
            System.out.println(ex.toString());
            log.error(ex.toString());
        }
        return false;
    }


}