package com.springboot.letterbackend.user.dto;

import com.springboot.letterbackend.data.entity.User;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileResponseDTO {
    String name;
    String userId;
    String email;
    LocalDate birthDay;
    String description;
    String profileUrl;
    //String password;

    public UserProfileResponseDTO(User user) {
         UserProfileResponseDTO.builder()
                .userId(user.getUid())
                .name(user.getName())
                .email(user.getEmail())
                .birthDay(user.getBirthDay())
                .description(user.getDesctiption())
                .profileUrl(user.getProfileImgUrl())
                .build();

    }

}
