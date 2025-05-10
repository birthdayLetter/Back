package com.springboot.letterbackend.user.service.impl;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.FriendRepository;
import com.springboot.letterbackend.letter.repository.LetterRepository;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.dto.response.UserProfileResponseDTO;
import com.springboot.letterbackend.user.dto.request.UserProfileRequestDTO;
import com.springboot.letterbackend.user.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
public class UserProfileserviceImpl implements UserProfileService {

    Logger logger = LoggerFactory.getLogger(UserProfileserviceImpl.class);

    final private UserRepository userRepository;

    final private FriendRepository friendRepository;
    final private PasswordEncoder passwordEncoder;

    public UserProfileserviceImpl(UserRepository userRepository, FriendRepository friendRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.friendRepository = friendRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public UserProfileResponseDTO getUserProfile(User user) {
        UserProfileResponseDTO userProfileResponseDTO = new UserProfileResponseDTO(user);
        return userProfileResponseDTO;


    }

    @Override
    public UserProfileResponseDTO editUserProfile(UserProfileRequestDTO userProfileRequestDTO, User user) {

        logger.info(userProfileRequestDTO.getUserId());
        //edit profile info
        user.setBirthDay(userProfileRequestDTO.getBirthDay());
        user.setName(userProfileRequestDTO.getName());
        user.setDesctiption(userProfileRequestDTO.getDescription());
        user.setUid(userProfileRequestDTO.getUserId());
        user.setProfileImgUrl(userProfileRequestDTO.getProfileImgUrl());
        //password가 null 값이면, 변경하지 않고, 값이 있으면 변경한다
        if(userProfileRequestDTO.getPassword()!=null){
            user.setPassword(passwordEncoder.encode(userProfileRequestDTO.getPassword()));
        }else{
            user.setPassword(user.getPassword());
        }


        // update profile info
        userRepository.save(user);
        //
        UserProfileResponseDTO userProfileResponseDTO = new UserProfileResponseDTO(user);
        return userProfileResponseDTO;
    }
}
