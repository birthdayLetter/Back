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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class LetterServiceImpl implements LetterService {

    private  final LetterRepository letterRepository;
    private  final LetterTemplateRepository letterTemplateRepository;
    private  final UserRepository userRepository;

    public LetterServiceImpl(LetterRepository letterRepository, LetterTemplateRepository letterTemplateRepository, UserRepository userRepository) {
        this.letterRepository = letterRepository;
        this.letterTemplateRepository = letterTemplateRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<ResponseLetterPostDTO> getAllLetter(String toUserId) {
        String toUserName=userRepository.getUserByUid(toUserId).getUsername();
        List<Letter> letters=letterRepository.getLetterByToUserId(toUserId);
        List<ResponseLetterPostDTO> responseLetterPostDTOs=new ArrayList<>();
        letters.forEach(letter->{

            String fromUserName=userRepository.getUserById(Long.valueOf(letter.getFromUserId())).getUsername();
            responseLetterPostDTOs.add(new ResponseLetterPostDTO(letter,fromUserName,toUserName,letter.getCreatedDate()));

        });

        return responseLetterPostDTOs;

    }


    @Override
    public List<ResponseLetterPostDTO> getAllLetterByYear(int year,long toUserId) {
        String toUserName=userRepository.getUserById(toUserId).getUsername();
        List<Letter> letters=letterRepository.getLetterByYearAndToUserId(year, String.valueOf(toUserId));
        List<ResponseLetterPostDTO> responseLetterPostDTOs=new ArrayList<>();
        letters.forEach(letter->{
            log.info(letter.getFromUserId()+"->"+letter.getToUserId()+"->"+letter.getCreatedDate());
            String fromUserName=userRepository.getUserById(Long.valueOf(letter.getFromUserId())).getUsername();
            responseLetterPostDTOs.add(new ResponseLetterPostDTO(letter,fromUserName,toUserName,letter.getCreatedDate()));

        });

        return responseLetterPostDTOs;

    }

    @Override
    public void sendLetter(User user, RequestLetterPostDTO responseLetterPostDTO) {
        Letter letter=new Letter();
        log.info(responseLetterPostDTO.getToUser());
        letter.setContent(responseLetterPostDTO.getContent());
        letter.setFromUserId(String.valueOf(user.getId()));

        User toUser=userRepository.getUserByUid(responseLetterPostDTO.getToUser());
        letter.setToUserId(String.valueOf(toUser.getId()));

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
