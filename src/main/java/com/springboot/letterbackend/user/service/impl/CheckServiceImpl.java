package com.springboot.letterbackend.user.service.impl;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.service.CheckService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CheckServiceImpl implements CheckService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;

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
        if(passwordEncoder.matches(enterPassword,user.getPassword())){
            return true;
        }
        return false;


    }
}
