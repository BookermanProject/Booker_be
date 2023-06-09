package com.sparta.booker.domain.book.dto;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.event.entity.Event;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Document(indexName = "book", createIndex = true)
// @Mapping(mappingPath = "static/mappings/es-mappings.json")
// @Setting(settingPath = "static/settings/es-settings.json")
@NoArgsConstructor
public class BookDto {
    private Long id;
    private String bookName;
    private String author;
    private String publisher;
    private String pub_date;
    private String category;
    private String introduction;
    private double star;
    private String img_url;
    private LocalDateTime modification_time;
    private LocalDateTime insertion_time;
    private Event eventid;
    private int like_count;

    @Builder
    public BookDto(Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
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

    // @QueryProjection
    // public BookDto(Long id, String bookName, String authors, String publisher, String pub_date,
    //                String category, String introduction, int star, String img_url, LocalDateTime modification_time,
    //     LocalDateTime insertion_time) {
    //     this.id = id;
    //     this.bookName = bookName;
    //     this.author = authors;
    //     this.publisher = publisher;
    //     this.pub_date = pub_date;
    //     this.category = category;
    //     this.introduction = introduction;
    //     this.star = star;
    //     this.img_url = img_url;
    //     this.modification_time = modification_time;
    //     this.insertion_time = insertion_time;
    // }

}

