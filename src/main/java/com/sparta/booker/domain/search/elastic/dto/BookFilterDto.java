package com.sparta.booker.domain.search.elastic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookFilterDto {
    private String query;
    private String sort;
    private String sortCategory;
    private Long id;
    private String book_name;
    private String author;
    private String publisher;
    private String pub_date;
    private String category;
    private String introduction;
    private double star;
    private String modification_time;
    private String insertion_time;
    private String like_count;
    private int page;
    private int size;


    public BookFilterDto(String query, String sort, String sortCategory, Long id, String book_name, String author, String publisher,  String pub_date, String category, String introduction, double star, String modification_time,
        String insertion_time, String like_count , int page, int size) {
        this.query = query;
        this.sort = sort;
        this.sortCategory = sortCategory;
        this.id = id;
        this.book_name = book_name;
        this.author = author;
        this.publisher = publisher;
        this.pub_date = pub_date;
        this.category = category;
        this.introduction = introduction;
        this.star = star;
        this.modification_time = modification_time;
        this.insertion_time = insertion_time;
        this.like_count = like_count;
        this.page = page;
        this.size = size;
    }
}
