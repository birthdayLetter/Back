package com.springboot.letterbackend.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;


@Getter
@Setter
public class KakaoRequestBody {
    @JsonProperty("grant_type")
    String grantType="authorization_code";
    @Value("kakao.rest_api_key")
    @JsonProperty("cilent_id")
    String clientId;
    @JsonProperty("redirect_uri")
    String redirectUri;
    String code;
}
