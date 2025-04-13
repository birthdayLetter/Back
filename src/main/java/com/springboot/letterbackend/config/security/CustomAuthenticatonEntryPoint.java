package com.springboot.letterbackend.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.springboot.letterbackend.user.dto.EntryPointErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticatonEntryPoint implements AuthenticationEntryPoint {

    private final Logger LOGGER= LoggerFactory.getLogger(CustomAuthenticatonEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("인증 실패로 Response.sendError");
        EntryPointErrorResponse errorResponse = new EntryPointErrorResponse();
        errorResponse.setMessage("인증이 실패하셨습니다");
        response.setStatus(401);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));

    }
}
