package com.springboot.letterbackend.user.controller;

import com.springboot.letterbackend.common.CommonResponse;
import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.user.dto.KakaoResponseDTO;
import com.springboot.letterbackend.user.dto.KakaoSinupRedirectResPonseDTO;
import com.springboot.letterbackend.user.dto.SignInResultDto;
import com.springboot.letterbackend.user.dto.SignUpResultDto;
import com.springboot.letterbackend.user.service.impl.CheckServiceImpl;
import com.springboot.letterbackend.user.service.impl.KakaoSignServiceImpl;

import com.springboot.letterbackend.user.service.impl.UserDetailsServiceImpl;
import jakarta.servlet.RequestDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public SignUpResultDto getUserInfo(@RequestParam("code") String code){
        logger.info("code:"+code);
        //1. 유저정보를 가져옴
        SignUpResultDto signUpResultDto=kakaoSignServiceImpl.sign(code);

        return signUpResultDto;
    }

}
