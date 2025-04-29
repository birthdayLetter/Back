package com.springboot.letterbackend.friend.dto.response;

import com.springboot.letterbackend.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

//친구 개인의 정보가 담긴 dto
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FriendInfoDTO {
    private Long id;
    private String name;
    private String uid;
    private LocalDate birthDay;
    private String description;

    public FriendInfoDTO(User friendUser) {
        this.id=friendUser.getId();
        this.name=friendUser.getName();
        this.uid=friendUser.getUid();
        this.birthDay=friendUser.getBirthDay();
        this.description=friendUser.getDesctiption();
    }
}
