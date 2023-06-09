package com.sparta.booker.domain.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.book.dto.LikeDto;
import com.sparta.booker.domain.book.entity.Book;
import com.sparta.booker.domain.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MySQLBookService {
	private final BookRepository bookRepository;

			// 책 리스트 가져오기/페이지네이션
		public List<BookDto> getBookList(Pageable pageable){
			long startTime = System.currentTimeMillis();
			List<BookDto> bookerlist =  bookRepository.findAll(pageable).stream().map(BookDto::new)
				.collect(Collectors.toList());
			long endTime = System.currentTimeMillis();
			System.out.println("페이지네이션 걸린 시간 : " + ((endTime-startTime)/1000.0)+ "초");
			return bookerlist;
		}

		public List<BookDto> searchFileterKeyword(Pageable pageable, String keyword){
			long startTime = System.currentTimeMillis();
			List<BookDto> bookDtoList = new ArrayList<>();

			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByInsertionTimeAsc(pageable,keyword,keyword).stream().map(BookDto::new).collect(
				Collectors.toList());

			long endTime = System.currentTimeMillis();

			System.out.println("좋아요순서 걸린 시간 : " + ((endTime-startTime)/1000.0)+ "초");
		return bookDtoList;
	}

	// 모든 책 검색하기(필터링추가)/좋아요순서

	public List<BookDto> searchfileter(Pageable pageable, String keyword, String order){
		long startTime = System.currentTimeMillis();
		List<BookDto> bookDtoList = new ArrayList<>();
		if(order.equals("ASC") || order.equals("asc")){
			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByLikeCountAsc(pageable,keyword,keyword).stream().map(BookDto::new).collect(
				Collectors.toList());
		}else {
			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByLikeCountDesc(pageable,keyword,keyword).stream()
				.map(BookDto::new).collect(Collectors.toList());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("좋아요순서 걸린 시간 : " + ((endTime-startTime)/1000.0)+ "초");
		return bookDtoList;
	}

	// 모든 책 검색하기(필터링추가)/별점순서
	public List<BookDto> searchFileterRating(Pageable pageable, String keyword, String order){
		long startTime = System.currentTimeMillis();
		List<BookDto> bookDtoList = new ArrayList<>();
		if(order.equals("ASC") || order.equals("asc")){
			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByStarAsc(pageable,keyword,keyword).stream().map(BookDto::new).collect(
				Collectors.toList());
		}else{
			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByStarDesc(pageable,keyword,keyword).stream().map(BookDto::new).collect(
				Collectors.toList());
		}
		long endTime = System.currentTimeMillis();
		System.out.println("별점순서 걸린 시간 : " + ((endTime-startTime)/1000.0)+"초");
		return bookDtoList;
	}


	//좋아요 카운트 수정
	@Transactional
	public LikeDto likeCount(Long bookid) {
		long startTime = System.currentTimeMillis();
		Book findbook = bookRepository.findById(bookid).orElseThrow(() -> {
			return new IllegalArgumentException("존재하지 않는 책입니다.");
		});
		findbook.upLikeCount();
		long endTime = System.currentTimeMillis();
		System.out.println("좋아요카운트 수정 걸린 시간 : "+ ((endTime-startTime)/1000.0)+"초");
		return new LikeDto(findbook.getId(), findbook.getLikeCount());
	}

	// 좋아요 Top10 리스트


}
