package com.springboot.letterbackend.user.service.impl;


import com.springboot.letterbackend.common.CommonResponse;
import com.springboot.letterbackend.config.security.JwtTokenProvider;
import com.springboot.letterbackend.user.dto.SignInResultDto;
import com.springboot.letterbackend.user.dto.SignUpResultDto;
import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.service.SignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

import static com.springboot.letterbackend.data.entity.LoginMethod.GENERAL;


@Service
public class SiginServiceImpl implements SignService {

    private final Logger LOGGER = LoggerFactory.getLogger(SiginServiceImpl.class);
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public PasswordEncoder passwordEncoder;

    @Autowired
    public SiginServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider, PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder=passwordEncoder;
    }


    @Override
    public SignUpResultDto signUp(String id, String password, String name, String role, String imgUrl, LocalDateTime birthDay) {

        LOGGER.info("회원가입 정보 전달");
        User user;

        if(role.equalsIgnoreCase("admin")){
            user=User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .birthDay(birthDay)
                    .roles(Collections.singletonList("ROLE_ADMIN"))
                    .build();

        }
        else{
            user=User.builder()
                    .uid(id)
                    .name(name)
                    .password(passwordEncoder.encode(password))
                    .profileImgUrl(imgUrl)
                    .birthDay(birthDay)
                    .roles(Collections.singletonList("ROLE_USER"))
                    .build();
        }

        User savedUser=userRepository.save(user);
        SignUpResultDto signUpResultDto=new SignUpResultDto();

        LOGGER.info("userEntity 값이 들어왔는지 확인후 결과값 주입");
        if(!savedUser.getName().isEmpty()){
            LOGGER.info("정상처리 완료");
            setSuccessResult(signUpResultDto);
        }else{
            LOGGER.info("실패 완료");
            setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException {
        LOGGER.info("signDataHandler로 회원정보 요청");
        User user=userRepository.getByUid(id);
        LOGGER.info("id"+id);
        LOGGER.info("패스워드 비교 수행");
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException();
        }
        LOGGER.info("패스워드 일치");
        LOGGER.info("SignInResultDto객체 생성");
        SignInResultDto signInResultDto= SignInResultDto.builder()
                .token(jwtTokenProvider.craftToken(String.valueOf(user.getUid()), user.getRoles()))
                .build();

        LOGGER.info("SignInResiltDto 객체에 값 주입");
        setSuccessResult(signInResultDto);

        return signInResultDto;
    }
    
    //DTO쪽 메서드로 분리하기
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
