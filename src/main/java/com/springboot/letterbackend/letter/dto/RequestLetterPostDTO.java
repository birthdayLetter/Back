package com.springboot.letterbackend.letter.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLetterPostDTO {
    long letterId;
    String fromUser;//받는사람
    String toUser;//보내는 사람
    String content;
    LocalDateTime date;
    long letterTemplateId;


}
