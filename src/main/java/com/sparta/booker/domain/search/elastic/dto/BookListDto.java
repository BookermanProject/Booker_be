package com.sparta.booker.domain.search.elastic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
public class BookListDto {
    private Integer page;
    private List<BookDto> books = new ArrayList<>();

    public BookListDto(List<BookDto> entities, Integer page) {
        this.books = entities;
        this.page = page;
    }
}