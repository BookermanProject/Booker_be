package com.sparta.booker.repository;

import com.sparta.booker.dto.BookRequestDto;
import com.sparta.booker.entity.Book;

import java.util.List;

public interface BookSearch {
    List<Book> QueryDslTest(BookRequestDto bookRequestDto);
}
