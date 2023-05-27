package com.sparta.booker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupRequestDto {

    private String userId;
    private String password;
    private String address;
}
