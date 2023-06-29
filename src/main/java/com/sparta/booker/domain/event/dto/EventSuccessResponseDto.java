package com.sparta.booker.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class EventSuccessResponseDto {
    private Long eventId;
    private String eventDate;
    private String eventTime;
}
