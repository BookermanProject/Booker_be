package com.sparta.booker.domain.search.elastic.entity;

import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.sparta.booker.domain.search.querydsl.dto.BookDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "booker")
@Mapping(mappingPath = "static/mappings/es-mappings.json")
@Setting(settingPath = "static/settings/es-settings.json")
public class Book {

    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(type = FieldType.Text)
    private String bookName;

    @Field(type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Text)
    private String publisher;

    @Field(type = FieldType.Text)
    private String pubDate;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Text)
    private String introduction;

    @Field(type = FieldType.Text)
    private double star;

    @Field(type = FieldType.Text)
    private String imgUrl;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime modificationTime;

    @Field(type = FieldType.Date, format = {DateFormat.date_hour_minute_second_millis, DateFormat.epoch_millis})
    private LocalDateTime insertionTime;

    @Field(type = FieldType.Text)
    private int likeCount;

    @Builder
    public Book(BookDto bookDto) {
        this.bookName = bookDto.getBookName();
        this.author = bookDto.getAuthor();
        this.publisher = bookDto.getPublisher();
        this.pubDate = bookDto.getPub_date();
        this.category= bookDto.getCategory();
        this.introduction = bookDto.getIntroduction();
        this.star= bookDto.getStar();
        this.imgUrl= bookDto.getImg_url();
    }

    public void upLikeCount(){
        this.likeCount = likeCount + 1;
    }
}
