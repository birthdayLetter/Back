package com.springboot.letterbackend.user.controller;

import com.springboot.letterbackend.user.service.impl.KakaoSignServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign-api/kakao")
public class kakaoUserSignController {
    private final Logger logger = LoggerFactory.getLogger(kakaoUserSignController.class);
    public final KakaoSignServiceImpl kakaoSignServiceImpl;

    public kakaoUserSignController(KakaoSignServiceImpl kakaoSignServiceImpl) {
        this.kakaoSignServiceImpl = kakaoSignServiceImpl;
    }


    @GetMapping("/sign-up")
    public String signUp(){
        return "redirect:https://kauth.kakao.com/oauth/authorize";
    }

    @GetMapping("/oauth")
    public String getUserInfo(@RequestParam("code") String code){
        //유저정보를 가져옴
        kakaoSignServiceImpl.getUserInfo(code);
        // 기존유저이면 로그인 아니면 회원가입 진행
        kakaoSignServiceImpl.signUpOrSignIn();
        return "redirect:https://kauth.kakao.com/oauth/authorize";

    }

}
