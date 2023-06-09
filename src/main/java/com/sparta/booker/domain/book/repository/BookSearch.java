package com.sparta.booker.domain.book.repository;

import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.book.entity.Book;

import java.util.List;

public interface BookSearch {
    List<Book> QueryDslTest(BookDto bookRequestDto);
}
