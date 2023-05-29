package com.sparta.booker.domain.book.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.sparta.booker.domain.book.entity.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;


@Getter
@Setter
@Document(indexName = "book", createIndex = true)
@Mapping(mappingPath = "static/mappings/es-mappings.json")
@Setting(settingPath = "static/settings/es-settings.json")
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String book_name;
    private String authors;
    private String publisher;
    private String publication_year;
    private String isbn13;
    private String isbn13Set;
    private String add_sign;
    private String book_cnt;
    private String kdc_num;
    private String total_num;
    private String rental_num;
    private String create_date;

    @Builder
    public BookDto(Book book) {
        this.id = book.getId();
        this.book_name = book.getBookName();
        this.authors = book.getAuthors();
        this.publisher = book.getPublisher();
        this.publication_year = book.getPublicationYear();
        this.isbn13 = book.getIsbn13();
        this.isbn13Set = book.getIsbn13Set();
        this.add_sign = book.getAddSign();
        this.book_cnt = book.getBookCnt();
        this.kdc_num = book.getKdcNum();
        this.total_num = book.getTotalNum();
        this.rental_num = book.getRentalNum();
        this.create_date = book.getCreateDate();
    }

    @QueryProjection
    public BookDto(Long id, String bookName, String authors, String publisher, String publicationYear,
                   String isbn13, String isbn13Set, String addSign, String bookCnt, String kdcNum,
                   String totalNum, String rentalNum, String createDate) {
        this.id = id;
        this.book_name = bookName;
        this.authors = authors;
        this.publisher = publisher;
        this.publication_year = publicationYear;
        this.isbn13 = isbn13;
        this.isbn13Set = isbn13Set;
        this.add_sign = addSign;
        this.book_cnt = bookCnt;
        this.kdc_num = kdcNum;
        this.total_num = totalNum;
        this.rental_num = rentalNum;
        this.create_date = createDate;
    }
}

