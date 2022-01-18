package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import com.dasom.gongtalk.domain.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class TokenProvider {

    private final AppProperties appProperties;
    private final CustomUserDetailsService userDetailsService;
    private int tokenDurationMin = 30;


    public String createToken(User user){
        Date now = new Date();
        Date expirationTime =new Date(now.getTime()+ Duration.ofMinutes(tokenDurationMin).toMillis());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, appProperties.getAuth().getTokenSecret())
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserById(this.getUserIdFromToken(token));
        return new DeviceNumAuthenticationToken(userDetails, userDetails.getAuthorities());
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
            Jws<Claims> claims = Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception ex){
            System.out.println(ex.toString());
            log.error(ex.toString());
        }
        return false;
    }


}