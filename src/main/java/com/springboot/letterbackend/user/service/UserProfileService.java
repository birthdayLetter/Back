package com.springboot.letterbackend.user.service;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.user.dto.response.UserProfileResponseDTO;
import com.springboot.letterbackend.user.dto.request.UserProfileRequestDTO;

public interface UserProfileService {
    //유저 기본정보를 불러오는 서비스
    UserProfileResponseDTO getUserProfile(User user);

    // 유저 개인 정보를 수정하는 서비스
    UserProfileResponseDTO editUserProfile(UserProfileRequestDTO userProfileReponseDTO, User user);
    // 유저가 편지를 클릭 했을때 읽었다고 상태를 수정합니다.
}
