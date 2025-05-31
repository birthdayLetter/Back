package com.springboot.letterbackend.notify.common;

public enum NotifyMessage {
    NEW_FRIEND_REQUEST("새로운 친구 요청이 있습니다."),
    NEW_FRIEND_RESPONSE("친구 수락이 되었습니다."),
    NEW_LETTER_REQUEST("새로운 편지가 도착했습니다");
    private String message;

    NotifyMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
