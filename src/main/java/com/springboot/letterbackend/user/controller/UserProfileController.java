package com.springboot.letterbackend.user.controller;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.user.dto.request.AuthRequestDTO;
import com.springboot.letterbackend.user.dto.response.UserProfileResponseDTO;
import com.springboot.letterbackend.user.dto.request.UserProfileRequestDTO;
import com.springboot.letterbackend.user.service.CheckService;
import com.springboot.letterbackend.user.service.UserProfileService;
import com.springboot.letterbackend.user.service.impl.ProfileServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

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
    private final ProfileServiceImpl profileService;
    private final CheckService checkService;



    public UserProfileController(UserProfileService userProfileService, ProfileServiceImpl profileService, CheckService checkService) {
        this.userProfileService = userProfileService;
        this.profileService = profileService;
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
    @RequestMapping(value = "/edit",method = {RequestMethod.PUT,RequestMethod.PATCH},consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public UserProfileResponseDTO editUserProfile(@RequestParam String name,
                                                  @RequestParam String userId,
                                                  @RequestParam String email,
                                                  @RequestParam LocalDate birthDay,
                                                  @RequestParam MultipartFile profileImg,
                                                  @RequestParam String description,
                                                  @RequestParam  String password, @AuthenticationPrincipal User user) throws IOException {

        String profileImgUrl=profileService.returnProfilePath(profileImg);
        UserProfileRequestDTO requstDTO=new UserProfileRequestDTO(name,userId,email,birthDay,description,profileImgUrl,password);
        UserProfileResponseDTO userProfileResponseDTO =userProfileService.editUserProfile(requstDTO,user);
        return userProfileResponseDTO;
    }


    @PostMapping(value = "/auth")
    public ResponseEntity<?> authPassword(@RequestBody AuthRequestDTO authRequestDTO, @AuthenticationPrincipal User user) {
        boolean flag= checkService.CheckPassword(authRequestDTO.getPassword(),user);
        logger.info(user.getEmail());
        logger.info("flag:"+flag);
        if(flag){
            ResponseEntity<?> responseEntity = ResponseEntity.ok().build();
            return responseEntity;

        }
        return ResponseEntity.badRequest().build();
    }

}
