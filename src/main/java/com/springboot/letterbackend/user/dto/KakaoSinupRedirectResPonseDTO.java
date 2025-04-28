package com.springboot.letterbackend.user.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor@AllArgsConstructor
@Builder
public class KakaoSinupRedirectResPonseDTO {
    String redirectUri;
}
