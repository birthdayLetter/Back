package com.springboot.letterbackend.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOOGER =  LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token= jwtTokenProvider.resolveToken(request);
        LOOGER.info("[doFilterInternal] token값 추출완료 token:"+token);
        LOOGER.info("token값 유효성 검사시작");
        if(token!=null&& jwtTokenProvider.validateToken(token)){
            Authentication autentication= jwtTokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(autentication);
            LOOGER.info("token값 유효성 체크 완료");
        }
        filterChain.doFilter(request,response);



    }
}
