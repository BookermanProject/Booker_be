package com.sparta.booker.domain.book.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.book.service.QueryDslBookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dsl/")
public class QueryDslBookController {

	private final QueryDslBookService queryDslBookService;
	// 모든 책 검색하기(필터링추가)/페이지네이션/ 순서없음
	@GetMapping("books/")
	public List<BookDto> searchFileterLike(String keyword,Pageable pageable, String category, String order){
		return queryDslBookService.getBookList(keyword, pageable,category, order);
	}

	//
	// //모든 책 검색하기(필터링추가)/페이지네이션/ 좋아요순서
	// @GetMapping("books/search/like/{keyword}/{order}")
	// public List<BookDto> searchFileterLike(Pageable pageable, @PathVariable String keyword, @PathVariable String order){
	// 	return mySQLBookService.searchfileter(pageable, keyword, order);
	// }
	//
	// //모든 책 검색하기(필터링추가)/페이지네이션/ 별점순서
	// @GetMapping("books/search/rating/{keyword}/{order}")
	// public List<BookDto> searchFileterRating(Pageable pageable, @PathVariable String keyword,  @PathVariable String order){
	// 	return mySQLBookService.searchFileterRating(pageable, keyword, order);
	// }
	//
	// //좋아요 TOP10
	// @PutMapping("book/{bookid}/like")
	// public LikeDto likeCount(@PathVariable Long bookid){
	// 	return mySQLBookService.likeCount(bookid);
	// }
	//
	// //실시간 검색어 리스트
	// @GetMapping("api/books/like")
	// public void likerealtime(){
	//
	// }

}
