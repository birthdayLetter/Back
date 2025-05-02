package com.springboot.letterbackend.user.service.impl;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.data.repository.FriendRepository;
import com.springboot.letterbackend.data.repository.LetterRepository;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.dto.response.UserProfileResponseDTO;
import com.springboot.letterbackend.user.dto.request.UserProfileRequestDTO;
import com.springboot.letterbackend.user.service.UserProfileService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserProfileserviceImpl implements UserProfileService {

    final private UserRepository userRepository;
    final private LetterRepository letterRepository;
    final private FriendRepository friendRepository;
    final private PasswordEncoder passwordEncoder;

    public UserProfileserviceImpl(UserRepository userRepository, LetterRepository letterRepository, FriendRepository friendRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.letterRepository = letterRepository;
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

        //edit profile info
        user.setBirthDay(userProfileRequestDTO.getBirthDay());
        user.setName(userProfileRequestDTO.getName());
        user.setDesctiption(userProfileRequestDTO.getDescription());
        user.setUid(userProfileRequestDTO.getUserId());
        user.setProfileImgUrl(userProfileRequestDTO.getProfileImgUrl());
        user.setPassword(passwordEncoder.encode(userProfileRequestDTO.getPassword()));

        // update profile info
        userRepository.save(user);
        //
        UserProfileResponseDTO userProfileResponseDTO = new UserProfileResponseDTO(user);
        return userProfileResponseDTO;
    }
}
