package com.springboot.letterbackend.friend.dto.request;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class FriendMessageDTO {
    private String username;   // 알림을 보낸 사람 (예: 나)
    private String message; // 알림을 받을 사람 (예: 상대방)
    // Getter / Setter
}