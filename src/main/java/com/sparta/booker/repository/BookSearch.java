package com.sparta.booker.repository;

import com.sparta.booker.dto.BookDto;
import com.sparta.booker.entity.Book;

import java.util.List;

public interface BookSearch {
    List<Book> QueryDslTest(BookDto bookRequestDto);
}
