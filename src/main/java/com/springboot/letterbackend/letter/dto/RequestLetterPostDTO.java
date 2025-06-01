package com.springboot.letterbackend.letter.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestLetterPostDTO {
    //long letterId;이것도 필요없음.
    //String fromUser;//보내는 사람
    String toUser;//받는사람
    String content;
    //LocalDateTime date; 이건 백엔드에서 처리하기
    long letterTemplateId;


}
