package com.sparta.booker.domain.book.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.booker.domain.book.entity.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;

@Getter
@Setter
@Document(indexName = "book", createIndex = true)
@Mapping(mappingPath = "static/mappings/es-mappings.json")
@NoArgsConstructor
public class BookDto {
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

    @Builder
    public BookDto(Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.authors = book.getAuthors();
        this.publisher = book.getPublisher();
        this.publicationYear = book.getPublicationYear();
        this.isbn13 = book.getIsbn13();
        this.isbn13Set = book.getIsbn13Set();
        this.addSign = book.getAddSign();
        this.bookCnt = book.getBookCnt();
        this.kdcNum = book.getKdcNum();
        this.totalNum = book.getTotalNum();
        this.rentalNum = book.getRentalNum();
        this.createDat = book.getCreateDat();
    }

    @QueryProjection
    public BookDto(Long id, String bookName, String authors, String publisher, String publicationYear,
                   String isbn13, String isbn13Set, String addSign, String bookCnt, String kdcNum,
                   String totalNum, String rentalNum, String createDat) {
        this.id = id;
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
