package com.springboot.letterbackend.friend.dto.response;

import com.springboot.letterbackend.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSearchFriendDto {
    private String userId;
    private String name;
    private String uid;
    private String description;

    public ResponseSearchFriendDto(User user) {
        this.userId = user.getUid();
        this.name = user.getName();
        this.uid = user.getUid();
        this.description=user.getDesctiption();
    }
}
