package com.sparta.booker.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    private String userId;
    private String password;
    private String address;
    private boolean admin = false;
    private String adminToken = "";

    public SignupRequestDto(String userId, String password, String address){
        this.userId = userId;
        this.password = password;
        this. address =address;
    }
}
