package com.sparta.booker.domain.search.querydsl.repository;

import static com.sparta.booker.domain.search.querydsl.entity.QBook.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.booker.domain.search.querydsl.entity.Book;
import com.sparta.booker.domain.search.querydsl.util.DslSortCursorUtil;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Repository
public class DSLBookRepository {

	private final JPAQueryFactory queryFactory;
	private final DslSortCursorUtil dslSortCursorUtil;
	public List<Book> findBookbyId(Pageable pageable){

		List<Long> coverIdxIds = queryFactory
			.select(book.id)
			.from(book)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		return queryFactory
			.selectFrom(book)
			.where(book.id.in(coverIdxIds))
			.fetch();

	}

	//기본//좋아요//별점
	public List<Book> findByBookList(String keyword, Pageable pageable, String orderBycategory, String order){
		return queryFactory.selectFrom(book).where(
				book.author.like("%"+keyword+"%").or(book.bookName.like("%"+keyword+"%")
				)).offset(pageable.getOffset()).
			limit(pageable.getPageSize()).orderBy(dslSortCursorUtil.getCursorSort(orderBycategory, order)).fetch();

	}

	// convering index
	public List<Book> findByBookListbyCovering(String keyword, Pageable pageable, String orderBycategory, String order) {
		List<Long> ids = queryFactory
			.select(book.id)
			.from(book)
			.where(book.bookName.like("%" + keyword + "%").or(book.author.like("%"+keyword+"%")))
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(dslSortCursorUtil.getCursorSort(orderBycategory, order))
			.fetch();

		if (CollectionUtils.isEmpty(ids)) {
			return new ArrayList<>();
		}

		return queryFactory
			.select(book)
			.from(book)
			.where(book.id.in(ids))
			.orderBy(dslSortCursorUtil.getCursorSort(orderBycategory, order))
			.fetch();
	}
}
