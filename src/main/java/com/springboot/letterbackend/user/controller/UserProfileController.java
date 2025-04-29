package com.springboot.letterbackend.user.controller;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.user.dto.UserProfileResponseDTO;
import com.springboot.letterbackend.user.dto.UserProfileRequestDTO;
import com.springboot.letterbackend.user.service.CheckService;
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
import org.springframework.web.bind.annotation.*;

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
    private final CheckService checkService;



    public UserProfileController(UserProfileService userProfileService, CheckService checkService) {
        this.userProfileService = userProfileService;
        this.checkService = checkService;
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
    @GetMapping(value = "/")// UserServie에서 유저 기본 정보를 불러옵니다
    public UserProfileResponseDTO getUserProfile(@AuthenticationPrincipal User user) {
        logger.info(user.getEmail());
        UserProfileResponseDTO userProfileResponseDTO=userProfileService.getUserProfile(user);
        logger.info(userProfileResponseDTO.getEmail());
        return userProfileResponseDTO;




    }

    // 마이페이지 정보를 수정합니다
    //accessToken 을 보내주세요
    @RequestMapping(value = "/edit",method = {RequestMethod.PUT,RequestMethod.PATCH})
    public UserProfileResponseDTO editUserProfile(@RequestBody UserProfileRequestDTO requstDTO, @AuthenticationPrincipal User user) {
        UserProfileResponseDTO userProfileResponseDTO =userProfileService.editUserProfile(requstDTO,user);
        return userProfileResponseDTO;
    }

    @PostMapping(value = "/auth")
    public boolean authPassword(@RequestBody String password,@AuthenticationPrincipal User user) {
        return checkService.CheckPassword(password,user);
    }

}
