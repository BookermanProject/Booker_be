package com.sparta.booker.domain.search.elastic.dto;


import javax.persistence.Index;

import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Document(indexName = "booker")
public class autoMakerDto {
    private String book_name;

    public autoMakerDto(String book_name) {
        this.book_name = book_name;
    }
}
