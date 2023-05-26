package com.sparta.booker.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private String createDat;
    public Book(String bookName, String authors, String publisher, String publicationYear,
                String isbn13, String isbn13Set, String addSign, String bookCnt, String kdcNum,
                String totalNum, String rentalNum, String createDat) {
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
        this.createDat = createDat;
    }
}
