package com.springboot.letterbackend.user.service;


import com.springboot.letterbackend.user.dto.SignInResultDto;
import com.springboot.letterbackend.user.dto.SignUpResultDto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SignService {
    SignUpResultDto signUp(String id, String password, String name, String url, LocalDateTime birthday);
    SignInResultDto signIn(String id, String password) throws  RuntimeException;


}
