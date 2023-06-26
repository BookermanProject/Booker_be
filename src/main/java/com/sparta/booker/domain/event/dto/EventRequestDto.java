package com.sparta.booker.domain.event.dto;

import com.sparta.booker.domain.event.entity.Event;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalTime;

@Getter
public class EventRequestDto {

    private Long id;
    private Long bookCnt;
    private Long bookTotalCnt;
    private String eventDate;
    private String eventTime;
    private Long bookId;
    private String isvalid;
    private String reason;

    public EventRequestDto(Event event) {
        this.id = event.getId();
        this.bookCnt = event.getBookCnt();
        this.bookTotalCnt = event.getBookTotalCnt();
        this.eventDate = event.getEventDate();
        this.eventTime = event.getEventTime();
        this.bookId = event.getBookId();
        this.isvalid = event.getIsvalid();
        this.reason = event.getReason();
    }
}
