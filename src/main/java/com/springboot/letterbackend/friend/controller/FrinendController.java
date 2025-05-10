package com.springboot.letterbackend.friend.controller;

import com.springboot.letterbackend.data.entity.User;
import com.springboot.letterbackend.friend.dto.request.FriendApplyRequestDto;
import com.springboot.letterbackend.friend.dto.response.FriendInfoDTO;
import com.springboot.letterbackend.friend.dto.response.FriendResultDto;
import com.springboot.letterbackend.friend.dto.response.ResponseSearchFriendDto;
import com.springboot.letterbackend.friend.service.impl.FriendServiceImpl;
import com.springboot.letterbackend.friend.service.impl.FriendWebSocketServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friend")
public class FrinendController {


   private final FriendServiceImpl friendService;

    public FrinendController(FriendServiceImpl friendService) {

        this.friendService = friendService;
    }
    /*
    내친구 목록을 전부 데려옵니다.
     */
    @GetMapping("/list")
    public List<FriendInfoDTO> getFriendList(@AuthenticationPrincipal User user){
        // 이걸 하려면..? 조회기능으로 추가하기가 있어야되요...
        //그리고 엔티티 일대다 관계설정되 해야되여... (모르는디.?)
        List<FriendInfoDTO>friendList=friendService.getFriendList(user.getId());
        return  friendList;
    }

    /*
     수락 대기중인 친구 목록만 보여줍니다
     */
    @GetMapping("/list/pending")
    public ResponseEntity<?>  getPendingFriendList(@Parameter @RequestParam Long userId){
        List<FriendInfoDTO> pendingFriendList=friendService.getPendingFriendList(userId);
        return ResponseEntity.ok().body(pendingFriendList);
    }

    /*
     조건에 맞는 친구 목록을 불러옵니다.
     */
    @GetMapping("/search")
    public ResponseEntity<?> getFriendSearchList(@RequestParam String searchParam){
        //이름 또는 아이디 검색 단어가 들어가있다면 친구목록을 불러옵니다.
        List<ResponseSearchFriendDto> responseSearchFriendList=friendService.serchUserByParam(searchParam);
        return ResponseEntity.ok().body(responseSearchFriendList);
    }





    /*
      - 특정 유저가 다른 유저를 상대로 친구 신청을 진행하면 , 친구 등록을 진행하되 Status를 PENDING으로 저장한다
     */
    @PostMapping("/apply")
    public ResponseEntity<FriendResultDto> apply(@RequestBody FriendApplyRequestDto friendApplyRequestDto) {
        FriendResultDto friendResultDto =friendService.applyFriend(friendApplyRequestDto.getFromUserId(), friendApplyRequestDto.getToUserId());
        return ResponseEntity.ok().body(friendResultDto);

    }

    @PostMapping("/add")
    public  ResponseEntity<FriendResultDto> addFriend(@RequestBody FriendApplyRequestDto friendApplyRequestDto) {
        FriendResultDto friendResultDto =friendService.addFriend(friendApplyRequestDto.getToUserId(), friendApplyRequestDto.getFromUserId());
        return ResponseEntity.ok().body(friendResultDto);
    }


}
