package com.springboot.letterbackend.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class KakaoResponseDTO {
    private String name;
    private String email;
    private String profileImg;

    public KakaoResponseDTO(String name, String email, String profileImg) {
        this.name = name;
        this.email = email;
        this.profileImg = profileImg;

    }

}
