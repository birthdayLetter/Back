package com.springboot.letterbackend.config.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value; //factory annotion으로 해야 에러안남.
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final Logger LOGGER= LoggerFactory.getLogger(JwtTokenProvider.class);
    private final UserDetailsService userDetailsService;

    @Value("${springboot.jwt.secret}")
    private String secretKey ="secretKey";
    private final long tokenValiditySeconds = 1000L*60*60;

    @PostConstruct
    protected void init(){
        LOGGER.info("[init] JwtTokenProvider 내 secretKey초기화 시작");
        secretKey= Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));

    }
    public String craftToken(String userEmail, List<String> roles){
        LOGGER.info("[createToken] 토큰 생성 시작");
        Claims claims = Jwts.claims().setSubject(userEmail);
        claims.put("roles", roles);
        Date now = new Date();

        String token=Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime()+tokenValiditySeconds))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
        LOGGER.info("[createToken] 토큰 생성 완료");
        return token;

    }

    public Authentication getAuthentication(String token){
        LOGGER.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails=userDetailsService.loadUserByUsername(this.getUserEmail(token));
        LOGGER.info("[getAuthentication] 토큰 인증 정보 초회 완료, Userdetails UserName:{}",userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUserEmail(String token){
        LOGGER.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        String info=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();

        LOGGER.info("[getUsername] 토큰기반회원구별정보추출완료,info:{}",info);
        return info;
    }
    public  String resolveToken(HttpServletRequest req){
        String bearerToken=req.getHeader("X-AUTH-TOKEN");
        return bearerToken;
    }

    public  boolean validateToken(String token){

        LOGGER.info("[validateToken]토큰 유효체크 시작");
        try{
            Jws<Claims> claims=Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        }catch (Exception e){
            LOGGER.info("[validateToken] 토큰 유료 체크 예외 발생");
            return false;
        }
    }
}
