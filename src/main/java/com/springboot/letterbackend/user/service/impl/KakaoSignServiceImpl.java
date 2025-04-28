package com.springboot.letterbackend.user.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.springboot.letterbackend.common.CommonResponse;
import com.springboot.letterbackend.config.security.JwtTokenProvider;
import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.dto.KakaoResponseDTO;
import com.springboot.letterbackend.user.dto.SignInResultDto;
import com.springboot.letterbackend.user.dto.SignUpResultDto;
import com.springboot.letterbackend.user.service.CheckService;
import com.springboot.letterbackend.user.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static com.springboot.letterbackend.data.entity.LoginMethod.KAKAO;


@Service
public class KakaoSignServiceImpl implements SignService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CheckService checkService;


    public PasswordEncoder passwordEncoder;

    @Value("kakao.userinfo.api")
    private String kakapAPI="https://kapi.kakao.com/v2/user/me";

    public KakaoSignServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, CheckService checkService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.checkService = checkService;
        this.passwordEncoder = passwordEncoder;
    }


    public String getAccessToken(String code){
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type","authorization_code");
        params.add("client_id","e140120f18ebca253dc9456e7b42857e");
        params.add("code",code);
        params.add("redirect_uri","http://localhost:8080/sign-api/kakao/oauth");


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, httpHeaders);

        JsonNode response=restTemplate.exchange("https://kauth.kakao.com/oauth/token",HttpMethod.POST,entity, JsonNode.class).getBody();
        String accessToken=response.get("access_token").asText();

        return accessToken;
    }

    public SignUpResultDto sign(String code){
        String accessToken=getAccessToken(code);
        logger.info("accessToken:{}",accessToken);
        KakaoResponseDTO kakaoResponseDTO=getUserInfo(accessToken);
        boolean isExisted=checkService.CheckEmail(kakaoResponseDTO.getEmail());
        SignUpResultDto signUpResultDto=new SignUpResultDto();

        if(isExisted){
            User user=userRepository.getByEmail(kakaoResponseDTO.getEmail());
            logger.info("user:{}",user.getRoles());
            if(user.getRoles().contains(String.valueOf(KAKAO))){
                // 가입된 유저가 카카오를 통해가입된 유저라면 로그인을 진행합니다.
                signUpResultDto=signIn(user.getEmail(),"");
            }else{
                //아니라면 중복됬다는 메시지를 보냅니다.
                setDuplicatedResult(signUpResultDto);
            }
        }else{
            //회원가입을 진햅하고, 로그인값을 반환합니다.
            signUp(kakaoResponseDTO.getEmail(),"",kakaoResponseDTO.getName(),kakaoResponseDTO.getProfileImg(),null);
            signUpResultDto=signIn(kakaoResponseDTO.getEmail(),"");
        }

        return signUpResultDto;
    }

    public KakaoResponseDTO getUserInfo(String accessToken){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.set("Authorization", "Bearer " + accessToken);
        httpHeaders.set("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
        HttpEntity entity = new HttpEntity(httpHeaders);
        JsonNode response=restTemplate.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.GET,entity,JsonNode.class).getBody();
        logger.info(response.toString());
        String name=response.findValue("kakao_account").findValue("nickname").asText();
        logger.info(name);
        String profileImgUrl=response.findValue("kakao_account").findValue("profile_image_url").asText();
        boolean hasEmail=response.findValue("kakao_account").findValue("has_email").asBoolean();
        boolean isEmailValid=response.findValue("kakao_account").findValue("is_email_valid").asBoolean();
        boolean isEmailVerified=response.findValue("kakao_account").findValue("is_email_verified").asBoolean();
        String email=setKakaoEmail(response,hasEmail,isEmailValid,isEmailVerified);

        KakaoResponseDTO kakaoResponseDTO=new KakaoResponseDTO(name,email,profileImgUrl);
        return kakaoResponseDTO;

    }


    public String setKakaoEmail(JsonNode response, boolean hasEmail, boolean isEmailValid, boolean isEmailVerified){
        String kakaoEmail=null;
        if(hasEmail&&isEmailValid&&isEmailVerified){
            kakaoEmail=response.get("kakao_account").get("email").asText();
        }
        return kakaoEmail;
    }




    @Override
    public SignUpResultDto signUp(String id, String password, String name, String url, LocalDate birthday) {
        User user;
        user=User.builder()
                .uid(UUID.randomUUID().toString().substring(0,8)) //임시로 만든다음에 나중에 수정하도록한다. 8자리로 한정함
                .password("")
                .profileImgUrl(url)
                .name(name)
                .email(id)
                .roles(Collections.singletonList(String.valueOf(KAKAO)))
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
        User user=userRepository.getByEmail(id);
        logger.info("id"+id);

        SignInResultDto signInResultDto= SignInResultDto.builder()
                .token(jwtTokenProvider.craftToken(String.valueOf(user.getEmail()), user.getRoles()))
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

    private void setDuplicatedResult(SignUpResultDto signUpResultDto) {
        signUpResultDto.setSuccess(false);
        signUpResultDto.setCode(CommonResponse.DUPLICATED.getCode());
        signUpResultDto.setMsg(CommonResponse.DUPLICATED.getMsg());

    }

}
