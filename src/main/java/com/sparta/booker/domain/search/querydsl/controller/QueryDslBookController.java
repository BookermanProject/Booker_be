package com.sparta.booker.domain.search.querydsl.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.booker.domain.search.querydsl.dto.BookDto;
import com.sparta.booker.domain.search.querydsl.dto.BookListDto;
import com.sparta.booker.domain.search.querydsl.service.QueryDslBookService;
import com.sparta.booker.domain.search.querydsl.util.RedisUtil;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dsl/")
public class QueryDslBookController {

	private final QueryDslBookService queryDslBookService;
	// 모든 책 검색하기(필터링추가)/페이지네이션/ 기본 인덱싱
	private final RedisUtil redisUtil;

	@GetMapping("books")
	public BookListDto search(Pageable pageable){
		long start = System.currentTimeMillis();
		BookListDto bookListDto = new BookListDto(queryDslBookService.getBookList(pageable),Long.parseLong(redisUtil.get("Count").toString()));
		long end = System.currentTimeMillis();
		System.out.println("걸린시간  : "+ (end-start));
		return  bookListDto;
	}

	@GetMapping("books/fileter")
	public List<BookDto> searchFileter(String keyword,Pageable pageable, String category, String order){
		return queryDslBookService.getBookListbyFilter(keyword, pageable,category, order);
	}
	//Fulltext index Like
	@GetMapping("books/fulltext/insertTime")
	public List<BookDto> searchFileterfulltextinsertTime(String keyword,Pageable pageable, String order){
		return queryDslBookService.getBookListbyFullTextInesertTime(keyword, pageable, order);
	}

	//Fulltext index Like
	@GetMapping("books/fulltext/Like")
	public List<BookDto> searchFileterfulltextLike(String keyword,Pageable pageable, String order){
		return queryDslBookService.getBookListbyFullTextLike(keyword, pageable, order);
	}

	//Fulltext index Star
	@GetMapping("books/fulltext/Star")
	public List<BookDto> searchFileterfulltextStar(String keyword,Pageable pageable, String order){
		return queryDslBookService.getBookListbyFullTextStar(keyword, pageable, order);
	}

	//Covering index
	@GetMapping("books/coveringIdx")
	public List<BookDto> searchFiletercoverIdx(String keyword,Pageable pageable, String category, String order){
		return queryDslBookService.getBookListbyCoverIdx(keyword, pageable,category, order);
	}



}
