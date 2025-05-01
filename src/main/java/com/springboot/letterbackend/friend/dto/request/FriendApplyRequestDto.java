package com.springboot.letterbackend.friend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FriendApplyRequestDto {
    private Long fromUserId;
    private Long toUserId;
}
