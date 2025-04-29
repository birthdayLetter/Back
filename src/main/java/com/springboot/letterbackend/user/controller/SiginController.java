package com.springboot.letterbackend.user.controller;


import com.springboot.letterbackend.user.dto.response.SignInResultDto;
import com.springboot.letterbackend.user.dto.response.SignUpResultDto;
import com.springboot.letterbackend.user.service.CheckService;
import com.springboot.letterbackend.user.service.impl.ProfileServiceImpl;
import com.springboot.letterbackend.user.service.SignService;
import io.swagger.v3.oas.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sign-api")
public class SiginController {

    private final Logger logger = LoggerFactory.getLogger(SiginController.class);
    private final SignService signService;
    private final ProfileServiceImpl profileService;
    private final CheckService checkService;

    @Autowired
    public SiginController(@Qualifier("siginServiceImpl")SignService signService, ProfileServiceImpl profileService, CheckService checkService) {
        this.signService = signService;
        this.profileService = profileService;
        this.checkService = checkService;
    }

    @PostMapping(value = "/sign-in")
    public SignInResultDto signIn(
           @Parameter(name = "email",required = true) @RequestParam String email,
           @Parameter(name="password",required = true) @RequestParam String password
    )throws RuntimeException{
        logger.info("로그인을 시도하고 있습니다 id :{}, pw:*",email);
        SignInResultDto signInResultDto = signService.signIn(email, password);
        if(signInResultDto.getCode()==0){
            logger.info("정상적으로 로그인 되었습니다 id:{},token:{}",email,signInResultDto.getToken());

        }
        return signInResultDto;
    }

    @PostMapping(value = "/check/email")
    public SignUpResultDto checkEmail( @Parameter(name = "email",required = true) @RequestBody String email){
        boolean isUserExisted =checkService.CheckEmail(email);
        SignUpResultDto signUpResultDto = new SignUpResultDto();
        if(isUserExisted){
            signUpResultDto.setSuccess(false);
            signUpResultDto.setMsg("이미 유저가 존재합니다");
            signUpResultDto.setCode(0);

        }
        signUpResultDto.setSuccess(true);
        signUpResultDto.setCode(1);
        signUpResultDto.setMsg("사용 가능한 이메일 입니다");

        return signUpResultDto;
    }


    @PostMapping(value = "/sign-up",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SignUpResultDto signUp(
            @Parameter(name = "email",required = true) @RequestParam String email,
            @Parameter(name = "password",required = true) @RequestParam String password,
            @Parameter(name = "name",required = true) @RequestParam String name,
            @RequestPart(name = "profileImg", required = false) @RequestParam  MultipartFile profileImg,
            @Parameter(name = "birthDay", required = true) @RequestParam LocalDate birthDay
    ) throws Exception {
        logger.info("회원가입을 수행합니다 id :{} password :** role:{}",email);

        String profileImgUrl = "";
        if(!profileImg.isEmpty()){
            profileImgUrl=profileService.returnProfilePath(profileImg);
        }

        //이메일 검색해서 유저가 있으면 유저가 이미 존재한다고 보내기
        boolean isUserExisted =checkService.CheckEmail(email);
        if(isUserExisted){
            SignUpResultDto signUpResultDto = new SignUpResultDto();
            signUpResultDto.setSuccess(false);
            signUpResultDto.setMsg("이미 유저가 존재합니다");
            signUpResultDto.setCode(0);
            return signUpResultDto;
        }

        SignUpResultDto signUpResultDto = signService.signUp(email, password, name, profileImgUrl, birthDay);
        logger.info("회원가입을 완료했습니다 id:{}",email);
        return signUpResultDto;
    }

    @GetMapping(value = "/exception")
    public void exceptionTest() throws RuntimeException{
        throw new RuntimeException("접근이 금지 되었습니다");
    }
    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<Map<String,String>> ExceptionHandler(RuntimeException e){
        HttpHeaders responseHeaders = new HttpHeaders();
        //responseHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        logger.error("ExceptionHandler 호출,{},{}",e.getCause(),e.getMessage());

        Map<String,String> map = new HashMap<>();
        map.put("error type",httpStatus.getReasonPhrase());
        map.put("code","400");
        map.put("message",e.getMessage());
        return new ResponseEntity<>(map,responseHeaders,httpStatus);
    }



}
