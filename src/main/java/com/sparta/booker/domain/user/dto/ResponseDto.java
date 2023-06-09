package com.sparta.booker.domain.user.dto;

import lombok.Getter;

@Getter
public class ResponseDto {

    String message;

    public ResponseDto(String message) {
        this.message = message;
    }
}
