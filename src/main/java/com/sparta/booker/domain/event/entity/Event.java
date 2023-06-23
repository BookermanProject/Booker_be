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

    //이벤트 발생 시간
    @Column(nullable = false)
    private String eventDate;

    @Column(nullable = false)
    private String eventTime;

    //책 총 개수
    @Column(nullable = false)
    private int book_total_cnt;

    //책 카운트
    @Column(nullable = false)
    private int book_cnt;

    //이벤트 취소 사유
    private String reason;

    @Column(nullable = false)
    private String isvalid;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private Book book;

    public Event(EventRequestDto requestDto, Book book) {
        this.eventDate = requestDto.getEventDate();
        this.eventTime = requestDto.getEventTime();
        this.book_total_cnt = requestDto.getBook_total_cnt();
        this.book_cnt = requestDto.getBook_cnt();
        this.isvalid = requestDto.getIsvalid();
        this.book = book;
    }

    public void update(int bookCnt) {
        this.book_cnt = bookCnt;
    }
}
