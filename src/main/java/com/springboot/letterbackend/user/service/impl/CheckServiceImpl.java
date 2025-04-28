package com.springboot.letterbackend.user.service.impl;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.controller.SiginController;
import com.springboot.letterbackend.user.service.CheckService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CheckServiceImpl implements CheckService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(CheckServiceImpl.class);

    public CheckServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean CheckEmail(String email) {
        User user=userRepository.getByEmail(email);
        if(user!=null)
            return true;
        else
            return false;
    }

    @Override
    public boolean CheckUid(String uid) {
        User user=userRepository.getByUid(uid);
        if(user!=null)
            return true;
        else
            return false;

    }

    @Override
    public boolean CheckPassword(String enterPassword,User user) {
        logger.info("비교작업을 수행합니다.");
        logger.info(enterPassword);
        return  passwordEncoder.matches(enterPassword,user.getPassword());


    }
}
