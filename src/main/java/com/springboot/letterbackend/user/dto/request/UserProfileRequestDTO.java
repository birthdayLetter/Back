package com.springboot.letterbackend.user.dto.request;

import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileRequestDTO {
    String name;
    String userId;
    String email;
    LocalDate birthDay;
    String description;
    String profileUrl;
    String password;
}
