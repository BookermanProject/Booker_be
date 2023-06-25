package com.sparta.booker.domain.search.elastic.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookEventDto {

    private Long bookId;
    private String bookName;
    private int likeCount;

    public BookEventDto(Long bookId, String bookName, int likeCount) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.likeCount = likeCount;
    }
}
