package com.springboot.letterbackend.letter.service.impl;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.letter.dto.RequestLetterPostDTO;
import com.springboot.letterbackend.letter.entity.LetterTemplate;
import com.springboot.letterbackend.letter.repository.LetterRepository;
import com.springboot.letterbackend.data.repository.UserRepository;
import com.springboot.letterbackend.letter.dto.ResponseLetterPostDTO;
import com.springboot.letterbackend.letter.entity.Letter;
import com.springboot.letterbackend.letter.repository.LetterTemplateRepository;
import com.springboot.letterbackend.letter.service.LetterService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class LetterServiceImpl implements LetterService {

    LetterRepository letterRepository;
    LetterTemplateRepository letterTemplateRepository;
    UserRepository userRepository;

    @Override
    public List<ResponseLetterPostDTO> getAllLetter(String toUserId) {
        String toUserName=userRepository.getUserByUid(toUserId).getUsername();
        List<Letter> letters=letterRepository.getLetterByToUserId(toUserId);
        List<ResponseLetterPostDTO> responseLetterPostDTOs=new ArrayList<>();
        letters.forEach(letter->{

            String fromUserName=userRepository.getUserById(Long.valueOf(letter.getFromUserId())).getUsername();
            responseLetterPostDTOs.add(new ResponseLetterPostDTO(letter,fromUserName,toUserName,letter.getLetterTemplate().getImgaeUrl()));

        });

        return responseLetterPostDTOs;

    }


    @Override
    public List<ResponseLetterPostDTO> getAllLetterByYear(int year,String toUserId) {
        String toUserName=userRepository.getUserByUid(toUserId).getUsername();
        List<Letter> letters=letterRepository.getLetterByYearAndToUserId(year,toUserId);
        List<ResponseLetterPostDTO> responseLetterPostDTOs=new ArrayList<>();
        letters.forEach(letter->{

            String fromUserName=userRepository.getUserById(Long.valueOf(letter.getFromUserId())).getUsername();
            responseLetterPostDTOs.add(new ResponseLetterPostDTO(letter,fromUserName,toUserName,letter.getLetterTemplate().getImgaeUrl()));

        });

        return responseLetterPostDTOs;

    }

    @Override
    public void sendLetter(User user, RequestLetterPostDTO responseLetterPostDTO) {
        Letter letter=new Letter();
        letter.setContent(responseLetterPostDTO.getContent());
        letter.setToUserId(String.valueOf(user.getId()));
        letter.setFromUserId(responseLetterPostDTO.getFromUser());
        LetterTemplate letterTemplate=letterTemplateRepository.findLetterById(responseLetterPostDTO.getLetterTemplateId());
        letter.setLetterTemplate(letterTemplate);
        LocalDateTime  now=LocalDateTime.now();
        letter.setYear(now.getYear());
        letter.setCreatedDate(now);
        letterRepository.save(letter);

    }

    @Override
    public  List<Integer> getLetterMain(Long id) {
        List<Integer> distinctYesrs=letterRepository.findDistinctYearsByToUserId(id);
        return distinctYesrs;
    }
    //


}
