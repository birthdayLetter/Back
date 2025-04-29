package com.springboot.letterbackend.user.dto.request;

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
    String clientId="e140120f18ebca253dc9456e7b42857e";
    @JsonProperty("redirect_uri")
    String redirectUri;
    String code;
}
