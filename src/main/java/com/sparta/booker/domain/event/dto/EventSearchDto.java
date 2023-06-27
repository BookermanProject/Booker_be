package com.sparta.booker.domain.event.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EventSearchDto {

    List<EventRequestDto> result = new ArrayList<>();


    public EventSearchDto(List<EventRequestDto> result) {
        this.result = result;
    }
}
