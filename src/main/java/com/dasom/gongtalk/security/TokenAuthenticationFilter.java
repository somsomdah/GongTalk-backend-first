package com.dasom.gongtalk.security;

import com.dasom.gongtalk.domain.user.User;
import com.dasom.gongtalk.repository.UserRepository;
import com.dasom.gongtalk.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenProvider tokenProvider;
    private UserRepository userRepository;
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

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

            Credentials credentials = (Credentials) userService.loadUserByUsername(user.get().getUsername()); ;
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(credentials, null, null);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request,response);

        } catch (Exception ex){
            System.out.println("[Exception] "+ex.toString());
            filterChain.doFilter(request, response);
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