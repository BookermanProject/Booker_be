package com.sparta.booker.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class EventSuccessDto {
    private String bookName;
    private String eventDate;
    private String eventTime;
}
