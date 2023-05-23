package com.sparta.booker.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.sparta.booker.dto.BookRequestDto;
import com.sparta.booker.entity.Book;
import com.sparta.booker.entity.QBook;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BookSearchImpl extends QuerydslRepositorySupport implements BookSearch {

    public BookSearchImpl () {
        super(Book.class);
    }

    @Override
    public List<Book> QueryDslTest(BookRequestDto bookRequestDto) {
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

