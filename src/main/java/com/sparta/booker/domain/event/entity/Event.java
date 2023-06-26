package com.sparta.booker.domain.event.entity;

import com.sparta.booker.domain.search.querydsl.entity.Book;
import com.sparta.booker.domain.event.dto.EventRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long bookId;
    @Column
    private Long bookCnt;

    @Column
    private Long bookTotalCnt;

    @Column
    private String eventDate;

    @Column
    private String eventTime;
    @Column
    private String isvalid;

    @Column
    private String reason;

    public Event(EventRequestDto requestDto, Long bookId) {
        this.eventDate = requestDto.getEventDate();
        this.eventTime = requestDto.getEventTime();
        this.bookTotalCnt = requestDto.getBookTotalCnt();
        this.bookCnt = requestDto.getBookCnt();
        this.isvalid = requestDto.getIsvalid();
        this.bookId = bookId;
    }

    public void update(Long bookCnt) {
        this.bookCnt = bookCnt;
    }
}
