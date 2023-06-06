package com.sparta.booker.domain.book.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.book.dto.LikeDto;
import com.sparta.booker.domain.book.service.MySQLBookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mysql/")
public class MySQLBookController {


	private final MySQLBookService mySQLBookService;

	@Autowired
	StringRedisTemplate redisTemplate;


	//책 리스트 가져오기/페이지네이션
	@GetMapping("/books")
	public List<BookDto> getBookList(Pageable pageable) {
		return mySQLBookService.getBookList(pageable);
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

	//좋아요 TOP10
	@PutMapping("book/{bookid}/like")
	public LikeDto likeCount(@PathVariable Long bookid) {
		return mySQLBookService.likeCount(bookid);
	}


	//실시간 검색어 리스트
	@GetMapping("api/books/like")
	public void likerealtime() {

	}


//	@GetMapping("/setFruit")
//	public String setFruit(@RequestParam String name) {
//
//		ValueOperations<String, String> ops = redisTemplate.opsForValue();
//		ops.set("fruit", name);
//		return "saved.";
//	}
//
//	@GetMapping("/getFruit")
//	public String getFruit() {
//		ValueOperations<String, String> ops = redisTemplate.opsForValue();
//		String fruitname = ops.get("fruit");
//		return fruitname;
//	}
}