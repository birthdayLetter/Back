package com.springboot.letterbackend.letter.controller;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.letter.dto.RequestLetterPostDTO;
import com.springboot.letterbackend.letter.dto.ResponseLetterPostDTO;
import com.springboot.letterbackend.letter.service.LetterService;
import com.springboot.letterbackend.notify.common.NotificationType;
import com.springboot.letterbackend.notify.repository.EmitterRepositoryImpl;
import com.springboot.letterbackend.notify.service.NotifyService;
import com.springboot.letterbackend.user.dto.response.UserProfileResponseDTO;
import com.springboot.letterbackend.user.service.UserProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/letter")
public class LetterController {

    private  final  LetterService letterService;
    private final NotifyService notifyService;
    private final UserProfileService userProfileService;
    private final EmitterRepositoryImpl emitterRepository;
    private final Logger logger = LoggerFactory.getLogger(LetterController.class);



    public LetterController(LetterService letterService, NotifyService notifyService, UserProfileService userProfileService, EmitterRepositoryImpl emitterRepository) {
        this.letterService = letterService;
        this.notifyService = notifyService;
        this.userProfileService = userProfileService;
        this.emitterRepository = emitterRepository;
    }
    //편지페이지의 메인입니다. 받은 편지의 년도수만 보내줍니다.
    @GetMapping("/letter/main")
    public List<Integer> getLetterMain(@AuthenticationPrincipal User user){
        logger.info("userId:{}",user.getId());
        return letterService.getLetterMain(user.getId());
    }


    // 지금 까지 받은 모든 편지를 검색합니다
    @GetMapping("/all")
    public List<ResponseLetterPostDTO> getAllLetter(@AuthenticationPrincipal User user){
        List<ResponseLetterPostDTO> letterList=letterService.getAllLetter(user.getUid());
        return letterList;
    }
    //연도별 편지를 최신순으로 검색합니다
    @GetMapping("/search")
    public List<ResponseLetterPostDTO> getAllLetterByYear(@AuthenticationPrincipal User user,@RequestParam int year){
        List<ResponseLetterPostDTO> letterList=letterService.getAllLetterByYear(year,user.getUid());
        return letterList;


    }
    //편지를 보냅니다.
    @PostMapping("/send")
    public ResponseEntity<?> sendLetter(@AuthenticationPrincipal User user, @RequestBody RequestLetterPostDTO responseLetterPostDTO){
        logger.info("responseLetterPostDTO:{}",responseLetterPostDTO.getContent());
        logger.info("responseLetterPostDTO:{}",responseLetterPostDTO.getLetterTemplateId());
        logger.info("responseLetterPostDTO:{}",responseLetterPostDTO.getFromUser());
        logger.info("responseLetterPostDTO:{}",responseLetterPostDTO.getToUser());
        letterService.sendLetter(user,responseLetterPostDTO);
        return ResponseEntity.ok().body("success");
//        User toUser=userProfileService.getUserProfileByUserId(responseLetterPostDTO.getToUser());
//        String receiverId=responseLetterPostDTO.getToUser();
//        Map<String, SseEmitter> emitterMap= emitterRepository.findAllEmitterStartWithByMemberId(receiverId);
//        if(!emitterMap.isEmpty()){
//            //sse가 연결되어 있으면
//            try{
//                notifyService.send(toUser, NotificationType.LETTER,"새로운 편지가 도착했어요!","확인시에 연결될 링크 그러니까 친구확인을 누르면 이동할 링크");
//            }catch (Exception e){
//                emitterRepository.deleteById(receiverId);
//            }
//
//        }else{
//            // 일단 저장해두기
//          //Notification저장하기
//        }




    }
}
