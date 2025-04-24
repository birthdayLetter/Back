package com.springboot.letterbackend.user.service;

public interface CheckService {
    boolean CheckEmail(String email);
    boolean CheckUid(String uid);
}
