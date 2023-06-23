package com.sparta.booker.domain.search.elastic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BookFilterDto {
    private String query;
    private String sort;
    private String sortCategory;

    public BookFilterDto(String query, String sort, String sortCategory) {
        this.query = query;
        this.sort = sort;
        this.sortCategory = sortCategory;

    }
}
