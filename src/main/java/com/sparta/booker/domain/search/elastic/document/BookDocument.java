package com.sparta.booker.domain.search.elastic.document;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.sparta.booker.domain.search.querydsl.entity.Book;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Document(indexName = "booker", createIndex = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Mapping(mappingPath = "static/mappings/es-mappings.json")
@Setting(settingPath = "static/settings/es-settings.json")
public class BookDocument {
    @Id
    @Field(type = FieldType.Keyword)
    private Long id;

    @Field(type = FieldType.Text)
    private String book_name;

    @Field(type = FieldType.Text)
    private String author;

    @Field(type = FieldType.Text)
    private String publisher;

    @Field(type = FieldType.Text)
    private String pub_date;

    @Field(type = FieldType.Text)
    private String category;

    @Field(type = FieldType.Text)
    private String introduction;

    @Field(type = FieldType.Keyword, fielddata = true)
    private double star;

    @Field(type = FieldType.Text)
    private String img_url;

    @Field(type = FieldType.Date)
    private LocalDateTime modification_time;

    @Field(type = FieldType.Date)
    private LocalDateTime insertion_time;

    @Field(type = FieldType.Keyword)
    private int like_count;

    @Builder
    public BookDocument(Book book) {
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

