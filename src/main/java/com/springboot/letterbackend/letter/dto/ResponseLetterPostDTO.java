package com.springboot.letterbackend.letter.dto;

import com.springboot.letterbackend.letter.entity.Letter;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseLetterPostDTO {
    long letterId;
    String fromUser;//받는사람
    String toUser;//보내는 사람
    String content;
    LocalDateTime date;

    public ResponseLetterPostDTO(Letter letter, String fromUserName, String toUserName, LocalDateTime createdDate) {
        this.letterId = letter.getId();
        this.fromUser = fromUserName;
        this.toUser = toUserName;
        this.content = letter.getContent();
        this.date = createdDate;
    }
    //String letterTemplateUrl;
    
}
