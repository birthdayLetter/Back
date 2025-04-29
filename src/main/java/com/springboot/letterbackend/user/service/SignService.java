package com.springboot.letterbackend.user.service;


import com.springboot.letterbackend.user.dto.response.SignInResultDto;
import com.springboot.letterbackend.user.dto.response.SignUpResultDto;

import java.time.LocalDate;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String url, LocalDate birthday);
    SignInResultDto signIn(String id, String password) throws  RuntimeException;


}
