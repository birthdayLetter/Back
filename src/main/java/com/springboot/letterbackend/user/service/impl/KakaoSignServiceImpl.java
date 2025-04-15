package com.springboot.letterbackend.user.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.springboot.letterbackend.common.CommonResponse;
import com.springboot.letterbackend.config.security.JwtTokenProvider;
import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.dto.KakaoRequestBody;
import com.springboot.letterbackend.user.dto.SignInResultDto;
import com.springboot.letterbackend.user.dto.SignUpResultDto;
import com.springboot.letterbackend.user.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.UUID;


@Service
public class KakaoSignServiceImpl implements SignService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public PasswordEncoder passwordEncoder;

    @Value("kakao.userinfo.api")
    private String kakapAPI;

    public KakaoSignServiceImpl(JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = passwordEncoder;
    }

    // 인가코드를 받기

    //토큰 받기
    public String signKakao(String code){
        //1. code로 post 신호 보내서 access Token 가져오기
        String acceaaToken=getAccessToken(code);
        //2. accessToken으로 유저 아이디 가져오기
        getUserInfo(acceaaToken);
        return null;
    }

    public String getAccessToken(String code){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders= new HttpHeaders();
        //바디에 값 집어넣기
        KakaoRequestBody body=new KakaoRequestBody();
        body.setCode(code);
        httpHeaders.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<KakaoRequestBody> request = new HttpEntity<>(body, httpHeaders);
        JsonNode response=restTemplate.postForEntity("https://kakao.com/oauth/token",request, JsonNode.class).getBody();
        String accessToken=response.get("access_token").asText();

        return accessToken;
    }

    public String getUserInfo(String accessToken){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        httpHeaders.set("Content-Type", "application/json");
        HttpEntity entity = new HttpEntity(httpHeaders);

        JsonNode response=restTemplate.exchange(kakapAPI, HttpMethod.GET,entity,JsonNode.class).getBody();
        String nickname = response.get("kakao_account").get("profile").get("nickname").asText();
        String thumbNailUrl = response.get("kakao_account").get("profile").get("thumbnail_image_url").asText();
        String email = response.get("kakao_account").get("email").asText();
        String name = response.get("kakao_account").get("name").asText();
        String birthDay = response.get("kakao_account").get("birthday").asText();
        String id = response.get("id").asText();

        logger.info("카카오 api를 통해 개인정보를 불러오는 데 성공했습니다. 생일 :{}",birthDay);

        return null;
    }

    public  String signUpOrSignIn(){
        boolean isExsited=IsExistedUser("");
        if(isExsited){
            //signUp("","","","");
        }else{
            //signIn("","");
        }
        return null;
    }

    // 이미 가입한 유저인지 아닌지 검사
    // 이걸 어떻게 검사해야하는지 감이 안옴.
    public boolean IsExistedUser(String username){

        return false;
    }


    // 카카오 정보로 회원가입을 진행합니다
    @Override
    public SignUpResultDto signUp(String id, String password, String name, String role, String url) {
       logger.info("카카오 정보를 기반으로 회원가입을 진행합니다.");
        User user;
        user=User.builder()
                .uid(String.valueOf(UUID.fromString(id))) //임시로 만든다음에 나중에 수정하도록한다.
                .password("")
                .profileImgUrl(url)
                .name(name)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();
        User savedUser=userRepository.save(user);
        SignUpResultDto signUpResultDto=new SignUpResultDto();

        logger.info("userEntity 값이 들어왔는지 확인후 결과값 주입");
        if(!savedUser.getName().isEmpty()){
            logger.info("정상처리 완료");
            setSuccessResult(signUpResultDto);
        }else{
            logger.info("실패 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException {
        logger.info("signDataHandler로 회원정보 요청");
        User user=userRepository.getByUid(id);
        logger.info("id"+id);
        logger.info("패스워드 비교 수행");
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException();
        }
        logger.info("패스워드 일치");
        logger.info("SignInResultDto객체 생성");
        SignInResultDto signInResultDto= SignInResultDto.builder()
                .token(jwtTokenProvider.craftToken(String.valueOf(user.getUid()), user.getRoles()))
                .build();

        logger.info("SignInResiltDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }

    private void setSuccessResult(SignUpResultDto signUpResultDto) {
        signUpResultDto.setSuccess(true);
        signUpResultDto.setCode(CommonResponse.SUCCESS.getCode());
        signUpResultDto.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailResult(SignUpResultDto signUpResultDto) {
        signUpResultDto.setSuccess(false);
        signUpResultDto.setCode(CommonResponse.FAIL.getCode());
        signUpResultDto.setMsg(CommonResponse.FAIL.getMsg());

    }
}
