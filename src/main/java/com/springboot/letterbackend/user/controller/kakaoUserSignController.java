package com.springboot.letterbackend.user.controller;

import com.springboot.letterbackend.user.dto.response.KakaoSinupRedirectResPonseDTO;
import com.springboot.letterbackend.user.dto.response.SignInResultDto;
import com.springboot.letterbackend.user.service.impl.CheckServiceImpl;
import com.springboot.letterbackend.user.service.impl.KakaoSignServiceImpl;

import com.springboot.letterbackend.user.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/sign-api/kakao")
public class kakaoUserSignController {
    private final Logger logger = LoggerFactory.getLogger(kakaoUserSignController.class);
    public final KakaoSignServiceImpl kakaoSignServiceImpl;
    public final UserDetailsServiceImpl userDetailsService;
    private final CheckServiceImpl checkServiceImpl;

    @Value("${kakao.rest_api_key}")
    String apikey;
    @Value("${kakao.redirect_uri}")
    String redirectUri;

    public kakaoUserSignController(KakaoSignServiceImpl kakaoSignServiceImpl, UserDetailsServiceImpl userDetailsService, CheckServiceImpl checkServiceImpl) {
        this.kakaoSignServiceImpl = kakaoSignServiceImpl;
        this.userDetailsService = userDetailsService;
        this.checkServiceImpl = checkServiceImpl;
    }


    @GetMapping("/sign-up")
    public KakaoSinupRedirectResPonseDTO signUp(){

        KakaoSinupRedirectResPonseDTO resPonseDTO=new KakaoSinupRedirectResPonseDTO();
        logger.info(apikey+"");
        resPonseDTO.setRedirectUri("https://kauth.kakao.com/oauth/authorize?client_id="+apikey+"&redirect_uri="+redirectUri+"&response_type=code");

        return resPonseDTO;
    }

    @GetMapping("/oauth")
    public ResponseEntity<Void> getUserInfo(@RequestParam("code") String code, HttpSession session){
        logger.info("code:"+code);
        //1. 유저정보를 가져옴
        SignInResultDto signInResultDto=kakaoSignServiceImpl.sign(code);

        // 세션에 토큰 저장
        logger.info(signInResultDto.getToken());
        session.setAttribute("OAUTH_TOKEN", signInResultDto.getToken());

        // 클라이언트를 프론트엔드 콜백 URL로 리다이렉트
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("http://localhost:3000/oauth/callback")); // 예시 프론트 URL
        return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 리다이렉트
    }


    @GetMapping("/auth/oauth-token")
    public SignInResultDto getOAuthToken(HttpSession session) {
        String token = (String) session.getAttribute("OAUTH_TOKEN");
        logger.info("세션에서 가져온 토큰"+token);

        SignInResultDto resultDto = new SignInResultDto();
        resultDto.setToken(token);
        return resultDto;
    }



}
