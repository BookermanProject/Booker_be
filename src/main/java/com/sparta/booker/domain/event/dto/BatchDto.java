package com.sparta.booker.domain.event.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class BatchDto {
    private String eventDate;
    private String eventTime;
    private Long bookId;
    private Long eventId;
    private String userId;
}
