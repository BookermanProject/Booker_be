package com.sparta.booker.domain.search.elastic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class BookListDto {
    private long page;
    private List<BookDto> books;
    public BookListDto(List<BookDto> entities, long page) {
        this.books = entities;
        this.page = page;
    }
}