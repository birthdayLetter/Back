package com.springboot.letterbackend.user.service;

import com.springboot.letterbackend.data.entity.User;

public interface CheckService {
    boolean CheckEmail(String email);
    boolean CheckUid(String uid);
    boolean CheckPassword(String password, User user);
}
