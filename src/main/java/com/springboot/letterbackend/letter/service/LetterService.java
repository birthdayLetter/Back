package com.springboot.letterbackend.letter.service;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.letter.dto.RequestLetterPostDTO;
import com.springboot.letterbackend.letter.dto.ResponseLetterPostDTO;

import java.util.List;

public interface LetterService {

    //유저의 모든 편지를 검색한다
    public List<ResponseLetterPostDTO> getAllLetter(String toUserId);
    //유저의 특정년도 편지를 검색
    public List<ResponseLetterPostDTO> getAllLetterByYear(int year, long toUserId);
    public void sendLetter(User user,RequestLetterPostDTO responseLetterPostDTO);

    public List<Integer> getLetterMain(Long id);
}


