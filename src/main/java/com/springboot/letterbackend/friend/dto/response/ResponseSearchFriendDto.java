package com.springboot.letterbackend.friend.dto.response;

import com.springboot.letterbackend.data.entity.User;

public class ResponseSearchFriendDto {
    private long userId;
    private String name;
    private String uid;
    private String description;

    public ResponseSearchFriendDto(User user) {
        this.userId = user.getId();
        this.name = user.getName();
        this.uid = user.getUid();
        this.description=user.getDesctiption();
    }
}
