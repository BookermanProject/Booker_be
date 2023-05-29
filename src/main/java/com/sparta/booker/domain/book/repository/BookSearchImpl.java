package com.sparta.booker.domain.book.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.book.entity.QBook;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BookSearchImpl extends QuerydslRepositorySupport implements BookSearch {

    public BookSearchImpl () {
        super(Book.class);
    }

    @Override
    public List<Book> QueryDslTest(BookDto bookRequestDto) {
        QBook book = QBook.book;
        JPQLQuery<Book> query = from(book);

        query.where( bookNameEq(bookRequestDto.getBookName(), book));

        return query.fetch();
    }


    private BooleanExpression bookNameEq(String bookName, QBook book ) {
        if(bookName.isEmpty())
            return null;
        return book.bookName.eq(bookName);
    }
}

