package com.sparta.booker.user.dto;

import lombok.Getter;

@Getter
public class ResponseDto {

    String message;

    public ResponseDto(String message) {
        this.message = message;
    }
}
