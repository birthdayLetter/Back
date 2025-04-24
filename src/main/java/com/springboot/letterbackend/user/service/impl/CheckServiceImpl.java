package com.springboot.letterbackend.user.service.impl;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.service.CheckService;

public class CheckServiceImpl implements CheckService {

    final UserRepository userRepository;

    public CheckServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
}
