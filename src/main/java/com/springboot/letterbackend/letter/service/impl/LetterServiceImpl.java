package com.springboot.letterbackend.letter.service.impl;

import com.springboot.letterbackend.letter.repository.LetterRepository;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.letter.dto.ResponseLetterPostDTO;
import com.springboot.letterbackend.letter.entity.Letter;
import com.springboot.letterbackend.letter.repository.LetterTemplateRepository;
import com.springboot.letterbackend.letter.service.LetterService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LetterServiceImpl implements LetterService {

    LetterRepository letterRepository;
    LetterTemplateRepository letterTemplateRepository;
    UserRepository userRepository;

    @Override
    public List<ResponseLetterPostDTO> getAllLetter(long toUserId) {
        String toUserName=userRepository.getUserById(toUserId).getUsername();
        List<Letter> letters=letterRepository.getLettersByToUserId(toUserId);
        List<ResponseLetterPostDTO> responseLetterPostDTOs=new ArrayList<>();
        letters.forEach(letter->{

            String fromUserName=userRepository.getUserById(Long.valueOf(letter.getFromUserId())).getUsername();
            responseLetterPostDTOs.add(new ResponseLetterPostDTO(letter,fromUserName,toUserName,letter.getLetterTemplate().getImgaeUrl()));

        });

        return responseLetterPostDTOs;

    }

    @Override
    public List<ResponseLetterPostDTO> getAllLetterByYear(int year,long toUserId) {
        String toUserName=userRepository.getUserById(toUserId).getUsername();
        List<Letter> letters=letterRepository.getLettersByYearAndToUserId(year,toUserId);
        List<ResponseLetterPostDTO> responseLetterPostDTOs=new ArrayList<>();
        letters.forEach(letter->{

            String fromUserName=userRepository.getUserById(Long.valueOf(letter.getFromUserId())).getUsername();
            responseLetterPostDTOs.add(new ResponseLetterPostDTO(letter,fromUserName,toUserName,letter.getLetterTemplate().getImgaeUrl()));

        });

        return responseLetterPostDTOs;

    }
    //


}
