package com.springboot.letterbackend.user.controller;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.user.dto.UserProfileReponseDTO;
import com.springboot.letterbackend.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

/**
 * 유저 프로파일 변경 관련 컨트롤러 입니다.
 * 1. 유저 기본 정보 불러오기
 * 2. 유저 개인정보 수정하기
 * 3. 최근 온 편지 순으로 편지 10개 정도 보여주기
 * 4. 읽지 않은 알림 보여주기
 * 5.
 */
@RestController
@RequestMapping ("/user/profile")
public class UserProfileController {

    private final Logger logger = LoggerFactory.getLogger(UserProfileController.class);
    private final UserProfileService userProfileService;



    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }


    /**
     * 맨 처음 로딩할때, 유저의 기본 정보들을 불러 옵니다.
     * 1. 유저 개인 정보 : 이름 ,Uid, 나이, 생일, 이메일 등
     * 2. 유저가 최근에 받은 편지 리스트 최신순 10개
     *
     */
    @Operation(description = "나의 정보 조회하기")
    @ApiResponses({
            @ApiResponse( responseCode = "500",description = "서버에서 문제 발생")})
    @Parameters({
            @Parameter(name = "X-AUTH-TOKEN",required = true,in = ParameterIn.HEADER)
    })
    @GetMapping(value = "/")
    public UserProfileReponseDTO getUserProfile(@AuthenticationPrincipal User user) {
        logger.info(user.toString());

        // UserServie에서 유저 기본 정보를 불러옵니다
        return UserProfileReponseDTO.builder()
                .name(user.getName())
                .userId(user.getUid())
                .birthDay(user.getBirthDay())
                .description(user.getDesctiption())
                .email(user.getEmail())
                .profileUrl(user.getProfileImgUrl())
                .build();




    }

}
