package com.sparta.booker.domain.search.elastic.dto;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.Document;

import com.sparta.booker.domain.search.querydsl.entity.Book;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Document(indexName = "booker", createIndex = true)
public class BookDto {
    private Long id;
    private String book_name;
    private String author;
    private String publisher;
    private String pub_date;
    private String category;
    private String introduction;
    private double star;
    private String img_url;
    private LocalDateTime modification_time;
    private LocalDateTime insertion_time;
    private int like_count;

    @Builder
    public BookDto(Book book) {

        this.id = book.getId();
        this.book_name = book.getBookName();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.pub_date = book.getPubDate();
        this.category = book.getCategory();
        this.introduction = book.getIntroduction();
        this.star = book.getStar();
        this.img_url = book.getImgUrl();
        this.modification_time = book.getModificationTime();
        this.insertion_time = book.getInsertionTime();
        this.like_count = book.getLikeCount();
    }
}

