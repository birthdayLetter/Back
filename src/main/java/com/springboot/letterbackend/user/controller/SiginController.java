package com.springboot.letterbackend.user.controller;


import com.springboot.letterbackend.user.dto.SignInResultDto;
import com.springboot.letterbackend.user.dto.SignUpResultDto;
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

import java.time.LocalDateTime;
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
           @Parameter(name = "id",required = true) @RequestParam String id,
           @Parameter(name="password",required = true) @RequestParam String password
    )throws RuntimeException{
        logger.info("로그인을 시도하고 있습니다 id :{}, pw:*",id);
        SignInResultDto signInResultDto = signService.signIn(id, password);
        if(signInResultDto.getCode()==0){
            logger.info("정상적으로 로그인 되었습니다 id:{},token:{}",id,signInResultDto.getToken());

        }
        return signInResultDto;
    }

    @PostMapping(value = "/sign-up",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public SignUpResultDto signUp(
            @Parameter(name = "id",required = true) @RequestParam String id,
            @Parameter(name = "password",required = true) @RequestParam String password,
            @Parameter(name = "name",required = true) @RequestParam String name,
            @Parameter(name = "role",required = true) @RequestParam String role,
            @RequestPart(name = "profileImg", required = false) @RequestParam  MultipartFile profileImg,
            @Parameter(name = "birthDay", required = true) @RequestParam LocalDateTime birthDay
    ) throws Exception {
        logger.info("회원가입을 수행합니다 id :{} password :** role:{}",id,role);

        String profileImgUrl = "";
        if(!profileImg.isEmpty()){
            profileImgUrl=profileService.returnProfilePath(profileImg);
        }
        SignUpResultDto signUpResultDto = signService.signUp(id, password, name, role,profileImgUrl, birthDay);
        logger.info("회원가입을 완료했습니다 id:{}",id);
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
