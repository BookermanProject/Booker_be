package com.sparta.booker.dto;

import com.sparta.booker.common.exception.CustomException;
import com.sparta.booker.common.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookFilterDto {
    private String query;
    private Integer sort;
    private Long id;
    private String bookName;
    private String authors;
    private String publisher;
    private String publicationYear;
    private String isbn13;
    private String isbn13Set;
    private String addSign;
    private String bookCnt;
    private String kdcNum;
    private String totalNum;
    private String rentalNum;
    private String createDat;
    private Integer totalRow;
    private Integer page;

    public void checkParameterValid() {
        if (query == null || query.isEmpty())
            throw new CustomException(ErrorCode.QUERY_NOT_FOUND);
        totalRow = totalRow == null ? 10 : totalRow;
        page = page == null ? 1 : page;
    }

    public BookFilterDto(String query, Integer sort, Long id, String bookName, String authors, String publisher, String publicationYear, String isbn13, String isbn13Set, String addSign, String bookCnt, String kdcNum, String totalNum, String rentalNum, String createDat, Integer totalRow, Integer page) {
        this.query = query;
        this.sort = sort;
        this.id = id;
        this.bookName = bookName;
        this.authors = authors;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.isbn13 = isbn13;
        this.isbn13Set = isbn13Set;
        this.addSign = addSign;
        this.bookCnt = bookCnt;
        this.kdcNum = kdcNum;
        this.totalNum = totalNum;
        this.rentalNum = rentalNum;
        this.createDat = createDat;
        this.totalRow = totalRow;
        this.page = page;
        checkParameterValid();
    }
}
