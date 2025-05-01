package com.springboot.letterbackend.friend.service.impl;

import com.springboot.letterbackend.data.repository.FriendRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class FriendWebSocketServiceImpl {

    private SimpMessagingTemplate messagingTemplate;


    public void sendToUser(String username, String message) {
        messagingTemplate.convertAndSendToUser(username, "/friend/notifications", message);
    }

    public void sendToFriend(String username, String message) {
        messagingTemplate.convertAndSendToUser(username, "/friend/notifications", message);
    }


}
