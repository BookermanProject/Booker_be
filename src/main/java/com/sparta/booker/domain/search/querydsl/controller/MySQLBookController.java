package com.sparta.booker.domain.search.querydsl.controller;

import com.sparta.booker.domain.search.querydsl.dto.BookDto;
import com.sparta.booker.domain.search.querydsl.dto.BookListDto;
import com.sparta.booker.domain.search.querydsl.dto.LikeDto;
import com.sparta.booker.domain.search.querydsl.service.MySQLBookService;
import com.sparta.booker.domain.user.util.UserDetailsImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mysql/")
public class MySQLBookController {
	private final MySQLBookService mySQLBookService;
	//책 리스트 가져오기/페이지네이션
	@GetMapping("/books")
	public BookListDto getBookList(Pageable pageable) {
		return mySQLBookService.getBookList(pageable);
	}

	// @GetMapping("/books/count")
	// public BookListDto getBookCount() {
	// 	return mySQLBookService.getBookList(pageable);
	// }

	//모든 책 검색하기(필터링추가)/페이지네이션/ 순서없음
	@GetMapping("books/search/{keyword}")
	public List<BookDto> searchFileterKeyword(Pageable pageable, @PathVariable String keyword){
		return mySQLBookService.searchFileterKeyword(pageable, keyword);
	}

	//모든 책 검색하기(필터링추가)/페이지네이션/ 좋아요순서
	@GetMapping("books/search/like/{keyword}/{order}")
	public List<BookDto> searchFileterLike(Pageable pageable, @PathVariable String keyword, @PathVariable String order) {
		return mySQLBookService.searchfileter(pageable, keyword, order);
	}

	//모든 책 검색하기(필터링추가)/페이지네이션/ 별점순서
	@GetMapping("books/search/rating/{keyword}/{order}")
	public List<BookDto> searchFileterRating(Pageable pageable, @PathVariable String keyword, @PathVariable String order) {
		return mySQLBookService.searchFileterRating(pageable, keyword, order);
	}

	//좋아요 누르기
	@PutMapping("book/{bookid}/like")
	public LikeDto likeCount(@PathVariable Long bookid, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return mySQLBookService.likeCount(bookid, userDetails.getUser());
	}

	// 좋아요 누른 목록 가지고오기
	@GetMapping("books/list")
	public List<BookDto> getLike(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return mySQLBookService.getLike(userDetails.getUser());
	}



}