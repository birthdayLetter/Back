package com.springboot.letterbackend.user.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class UserProfileRequestDTO {
    String name;
    String userId;
    String email;
    LocalDate birthDay;
    String description;
    String profileImgUrl;
    String password;

    public UserProfileRequestDTO(String name, String userId, String email, LocalDate birthDay,String description, String profileImgUrl, String password) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.birthDay = birthDay;
        this.description = description;
        this.profileImgUrl = profileImgUrl;
        this.password = password;

    }
}
