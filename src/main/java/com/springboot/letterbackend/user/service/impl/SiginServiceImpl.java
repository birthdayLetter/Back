package com.springboot.letterbackend.user.service.impl;


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

import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static com.springboot.letterbackend.data.entity.LoginMethod.GENERAL;


@Service
public class SiginServiceImpl implements SignService {

    private final Logger logger = LoggerFactory.getLogger(SiginServiceImpl.class);
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
    public SignUpResultDto signUp(String id, String password, String name,  String imgUrl, LocalDate birthDay) {

        logger.info("회원가입 정보 전달");
        User user;
        user=User.builder()
                .uid(UUID.randomUUID().toString().substring(0,8))
                .email(id)
                .name(name)
                .password(passwordEncoder.encode(password))
                .profileImgUrl(imgUrl)
                .birthDay(birthDay)
                .roles(Collections.singletonList(String.valueOf(GENERAL)))
                .build();


        User savedUser=userRepository.save(user);
        SignUpResultDto signUpResultDto=new SignUpResultDto();

        logger.info("userEntity 값이 들어왔는지 확인후 결과값 주입");
        if(!savedUser.getName().isEmpty()){
            logger.info("정상처리 완료");
            signUpResultDto.setSuccessResult(signUpResultDto);
        }else{
            logger.info("실패 완료");
            signUpResultDto.setFailResult(signUpResultDto);
        }
        return signUpResultDto;
    }

    @Override
    public SignInResultDto signIn(String id, String password) throws RuntimeException {
        logger.info("signDataHandler로 회원정보 요청");
        User user=userRepository.getByEmail(id);
        logger.info("id"+id);
        logger.info("패스워드 비교 수행");
        if(!passwordEncoder.matches(password,user.getPassword())){
            throw new RuntimeException();
        }
        logger.info("패스워드 일치");
        logger.info("SignInResultDto객체 생성");
        SignInResultDto signInResultDto= SignInResultDto.builder()
                .token(jwtTokenProvider.craftToken(String.valueOf(user.getEmail()), user.getRoles()))
                .build();

        logger.info("SignInResiltDto 객체에 값 주입");
        signInResultDto.setSuccessResult(signInResultDto);

        return signInResultDto;
    }

}
