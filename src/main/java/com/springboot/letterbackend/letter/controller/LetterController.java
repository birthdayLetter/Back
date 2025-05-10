package com.springboot.letterbackend.letter.controller;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.letter.dto.RequestLetterPostDTO;
import com.springboot.letterbackend.letter.dto.ResponseLetterPostDTO;
import com.springboot.letterbackend.letter.service.LetterService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/letter")
public class LetterController {

    private  final  LetterService letterService;

    public LetterController(LetterService letterService) {
        this.letterService = letterService;
    }


    // 지금 까지 받은 모든 편지를 검색합니다
    @GetMapping("/all")
    public List<ResponseLetterPostDTO> getAllLetter(@AuthenticationPrincipal User user){
        List<ResponseLetterPostDTO> letterList=letterService.getAllLetter(user.getUid());
        return letterList;
    }
    //연도별 편지를 최신순으로 검색합니다
    @GetMapping("/search/{year}")
    public List<ResponseLetterPostDTO> getAllLetterByYear(@AuthenticationPrincipal User user,@RequestParam int year){
        List<ResponseLetterPostDTO> letterList=letterService.getAllLetterByYear(year,user.getUid());
        return letterList;


    }
    //편지를 보냅니다.
    @PostMapping("/send")
    public void sendLetter(@AuthenticationPrincipal User user,@RequestBody RequestLetterPostDTO responseLetterPostDTO){

    }
}
