package com.dasom.gongtalk.security;

import com.dasom.gongtalk.domain.User;
import com.dasom.gongtalk.exception.ExceptionResponse;
import com.dasom.gongtalk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {

        try{
            String token = getTokenFromRequest(request);

            if (token == null || !StringUtils.hasText(token) && !tokenProvider.validateToken(token)){
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request,response);
                return;
            }

            Integer userId = tokenProvider.getUserIdFromToken(token);
            Optional<User> user = userRepository.findById(userId);
            if(user.isEmpty()){
                return;
            }

            Authentication auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request,response);


        } catch (Exception e){
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            ExceptionResponse eresp = new ExceptionResponse(e.toString(),"");
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
