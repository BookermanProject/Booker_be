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
    private LocalDateTime modificationTime;
    private LocalDateTime insertionTime;
    private int like_count;

    @Builder
    public BookDto(Book book) {
        this.id = book.getId();
        this.bookName = book.getBookName();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.pub_date = book.getPub_date();
        this.category = book.getCategory();
        this.introduction = book.getIntroduction();
        this.star = book.getStar();
        this.img_url = book.getImg_url();
        this.modificationTime = book.getModificationTime();
        this.insertionTime = book.getInsertionTime();
        this.like_count = book.getLikeCount();
    }


}

