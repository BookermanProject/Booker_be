package com.sparta.booker.domain.book.entity;


import com.sparta.booker.domain.event.entity.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bookName;
    private String authors;
    private String publisher;
    private String publicationYear;
    private String isbn13;
    private String isbn13Set;
    private String addSign;
    private String bookCnt;
    private String kdcNum;
    private String totalNum;
    private String rentalNum;
    private String createDate;

    @OneToOne(fetch = FetchType.LAZY)
    private Event event;

    public Book(String bookName, String authors, String publisher, String publicationYear,
                String isbn13, String isbn13Set, String addSign, String bookCnt, String kdcNum,
                String totalNum, String rentalNum, String createDate) {
        this.bookName = bookName;
        this.authors = authors;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.isbn13 = isbn13;
        this.isbn13Set = isbn13Set;
        this.addSign = addSign;
        this.bookCnt = bookCnt;
        this.kdcNum = kdcNum;
        this.totalNum = totalNum;
        this.rentalNum = rentalNum;
        this.createDate = createDate;
    }
}
