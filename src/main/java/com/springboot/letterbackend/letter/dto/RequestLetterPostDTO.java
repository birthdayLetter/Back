package com.springboot.letterbackend.letter.dto;

import lombok.*;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLetterPostDTO {
    String token;
    String content;
    String reciverId;
    String senderId;

}
