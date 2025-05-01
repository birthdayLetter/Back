package com.springboot.letterbackend.friend.controller;

import com.springboot.letterbackend.friend.service.impl.FriendWebSocketServiceImpl;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class FriendWebSocketController {
    //친구 알림 컨트롤러
    private final FriendWebSocketServiceImpl friendService;

    public FriendWebSocketController(FriendWebSocketServiceImpl friendService) {
        this.friendService = friendService;
    }


    @MessageMapping("/friend/apply")
    public void sendNotification(String username,String message) {
        friendService.sendToUser(username, message);

    }

    @MessageMapping("/friend/accpet")
    public void sendAccpet(String username,String message) {
        friendService.sendToFriend(username, message);

    }
}
