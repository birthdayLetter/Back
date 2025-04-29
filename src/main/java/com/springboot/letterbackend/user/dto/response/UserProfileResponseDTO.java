package com.springboot.letterbackend.user.dto.response;

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
         this.name=user.getName();
         this.userId=user.getUid();
         this.email=user.getEmail();
         this.birthDay=user.getBirthDay();
         this.description=user.getDesctiption();
         this.profileUrl=user.getProfileImgUrl();
    }

}
