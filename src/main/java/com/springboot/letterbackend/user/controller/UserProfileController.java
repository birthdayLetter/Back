package com.springboot.letterbackend.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 유저 프로파일 변경 관련 컨트롤러 입니다.
 * 1. 유저 기본 정보 불러오기
 * 2. 유저 개인정보 수정하기
 * 3. 최근 온 편지 순으로 편지 10개 정도 보여주기
 * 4. 읽지 않은 알림 보여주기
 * 5.
 */
@RestController(value = "/user/profile")
public class UserProfileController {

    /**
     * 맨 처음 로딩할때, 유저의 기본 정보들을 불러 옵니다.
     * 1. 유저 개인 정보 : 이름 ,Uid, 나이, 생일, 이메일 등
     * 2. 유저가 최근에 받은 편지 리스트 최신순 10개
     *
     */
    @GetMapping(value = "/")
    public UserProfileReponseDTO getUserDetails() {

    }

}
