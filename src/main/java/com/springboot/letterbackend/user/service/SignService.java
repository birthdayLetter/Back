package com.springboot.letterbackend.user.service;


import com.springboot.letterbackend.user.dto.SignInResultDto;
import com.springboot.letterbackend.user.dto.SignUpResultDto;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String role,String url);
    SignInResultDto signIn(String id, String password) throws  RuntimeException;


}
