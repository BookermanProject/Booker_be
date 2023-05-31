package com.sparta.booker.domain.event.entity;

import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.event.dto.EventRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //이벤트 발생 시간
    @Column(nullable = false)
    private LocalTime eventDate;

    //책 개수
    @Column(nullable = false)
    private int book_cnt;

    //이벤트 취소 사유
    private String reason;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "book_id")
    private Book book;

    public Event(EventRequestDto requestDto, Book book) {
        this.eventDate = requestDto.getEventDate();
        this.book_cnt = requestDto.getBook_cnt();
        this.book = book;
    }
}
