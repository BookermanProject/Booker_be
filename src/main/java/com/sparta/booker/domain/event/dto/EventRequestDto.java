package com.sparta.booker.domain.event.dto;

import lombok.Getter;
import java.time.LocalTime;

@Getter
public class EventRequestDto {

    private String eventDate;
    private String eventTime;
    private int book_total_cnt;
    private int book_cnt;
    private Long bookId;
    private String isvalid;


}
