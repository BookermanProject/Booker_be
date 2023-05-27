package com.sparta.booker.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class LoginRequestDto {

    private String userId;
    private String password;
}
