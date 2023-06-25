package com.sparta.booker.domain.event.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class EventDateDto {

    List<String> result = new ArrayList<>();


    public EventDateDto(List<String> result) {
        this.result = result;
    }
}
