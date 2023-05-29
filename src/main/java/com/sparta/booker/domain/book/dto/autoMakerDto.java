package com.sparta.booker.domain.book.dto;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Document(indexName = "book")
public class autoMakerDto {
    private String bookName;

    public autoMakerDto(String title) {
        this.bookName = title;
    }
}
