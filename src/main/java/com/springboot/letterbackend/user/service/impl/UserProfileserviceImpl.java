package com.springboot.letterbackend.user.service.impl;

import com.springboot.letterbackend.data.repository.FriendRepository;
import com.springboot.letterbackend.data.repository.LetterRepository;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.user.dto.UserProfileReponseDTO;
import com.springboot.letterbackend.user.service.UserProfileService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileserviceImpl implements UserProfileService {

    private UserRepository userRepository;
    private LetterRepository letterRepository;
    private FriendRepository friendRepository;


    @Override
    public UserProfileReponseDTO getUserProfile() {
        return null;
    }
}
