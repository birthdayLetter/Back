package com.springboot.letterbackend.user.dto.request;


import lombok.*;

import java.time.LocalDate;

@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommonSigUpRequestDTO {

    private String email;
    private String password;
    private String name;
    private String userId;
    private int year;
    private LocalDate birthday;




}
