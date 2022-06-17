package com.dasom.gongtalk.security;

import com.dasom.gongtalk.exception.ExceptionResponse;
import com.dasom.gongtalk.exception.UserNotAuthorizedException;
import com.dasom.gongtalk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

        try{
            String token = getTokenFromRequest(request);

            if (!StringUtils.hasText(token)){
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request,response);
                return;
            }

            Integer userId = tokenProvider.getUserIdFromToken(token);
            userService.getFromId(userId);

            Authentication auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request,response);

        } catch (UserNotAuthorizedException e) {

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");
            ExceptionResponse eresp = new ExceptionResponse(e.toString(), request.toString());
            response.getWriter().print(eresp.toJson());
            response.getWriter().flush();
        }
    }


    private String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        String tokenPrefix = "Bearer ";

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenPrefix)){
            return bearerToken.substring(tokenPrefix.length());
        }
        return null;
    }


}
