package com.dasom.gongtalk.security;

import com.dasom.gongtalk.config.AppProperties;
import com.dasom.gongtalk.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.descriptor.web.ContextResource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
public class TokenProvider {

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

    void setTokenDurationMin(){
        this.tokenDurationMin = this.appProperties.getAuth().getTokenDurationMin();
    }

    void setTokenSecret(){
        this.tokenSecret = this.appProperties.getAuth().getTokenSecret();
    }

    public String createToken(User user){
        Date now = new Date();
        Date expirationTime =new Date(now.getTime()+ Duration.ofMinutes(tokenDurationMin).toMillis());

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserById(this.getUserIdFromToken(token));
        return new DeviceNumAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    public Integer getUserIdFromToken(String token){
        Claims claims = Jwts.parser()
                .setSigningKey(tokenSecret)
                .parseClaimsJws(token)
                .getBody();
        return Integer.parseInt(claims.getSubject());
    }

    public boolean validateToken(String token){
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(tokenSecret).parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        }catch(Exception ex){
            System.out.printf("[Exception] %s : validateToken : %s%n",this.getClass().toString(),ex.toString());
        }
        return false;
    }

    public String createTokenWithUserId(Integer userId){
        Date now = new Date();
        Date expirationTime =new Date(now.getTime()+ Duration.ofMinutes(tokenDurationMin).toMillis());

        return Jwts.builder()
                .setSubject(Integer.toString(userId))
                .setIssuedAt(now)
                .setExpiration(expirationTime)
                .signWith(SignatureAlgorithm.HS256, tokenSecret)
                .compact();
    }


}