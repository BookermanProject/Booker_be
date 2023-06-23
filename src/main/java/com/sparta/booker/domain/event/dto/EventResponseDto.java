package com.sparta.booker.domain.event.dto;

import lombok.Getter;

@Getter
public class EventResponseDto {
    String message;

    public EventResponseDto(String message) {
        this.message = message;
    }
}
