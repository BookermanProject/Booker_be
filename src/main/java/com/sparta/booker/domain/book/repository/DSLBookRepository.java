package com.sparta.booker.domain.book.repository;

import static com.sparta.booker.domain.book.entity.QBook.*;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.book.util.DslSortCursor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class DSLBookRepository {

	private final JPAQueryFactory queryFactory;
	private final DslSortCursor dslSortCursor;

	//기본//좋아요//별점
	public List<Book> findByBookList(String keyword, Pageable pageable, String category, String order){
		return queryFactory.selectFrom(book).where(
			book.author.like("%"+keyword+"%").or(book.bookName.like("%"+keyword+"%")
		)).offset(pageable.getOffset()).
			limit(pageable.getPageSize()).orderBy(dslSortCursor.getCursorSort(category, order)).fetch();
	}

}
