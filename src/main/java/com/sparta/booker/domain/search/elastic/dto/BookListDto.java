package com.sparta.booker.domain.search.elastic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import com.sparta.booker.domain.search.elastic.document.BookDocument;

@Getter
@NoArgsConstructor
public class BookListDto {

    private Integer page;
    private List<BookDocument> books = new ArrayList<>();
    private String searchAfterSort;
    private Long searchAfterId;
    private Boolean status;

    public BookListDto(List<BookDto> entities, Integer page) {
        this.books = entities;
        this.page = page;
    }
}