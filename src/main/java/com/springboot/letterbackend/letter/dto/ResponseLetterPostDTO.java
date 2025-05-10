package com.springboot.letterbackend.letter.dto;

import com.springboot.letterbackend.letter.entity.Letter;
import lombok.*;

import java.time.LocalDate;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseLetterPostDTO {
    long letterId;
    String fromUser;//받는사람
    String toUser;//보내는 사람
    String content;
    LocalDate date;
    String letterTemplateUrl;

    public ResponseLetterPostDTO(Letter letter,String fromUser,String toUser,String letterTemplateUrl) {

    }
}
