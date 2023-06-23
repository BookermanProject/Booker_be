package com.sparta.booker.domain.search.querydsl.service;

import com.sparta.booker.domain.search.querydsl.dto.BookDto;
import com.sparta.booker.domain.search.querydsl.dto.BookListDto;
import com.sparta.booker.domain.search.querydsl.dto.LikeDto;
import com.sparta.booker.domain.search.querydsl.entity.Book;
import com.sparta.booker.domain.search.querydsl.entity.BookLike;
import com.sparta.booker.domain.search.querydsl.repository.BookLikeRepository;
import com.sparta.booker.domain.search.querydsl.repository.BookRepository;
import com.sparta.booker.domain.search.querydsl.util.RedisUtil;
import com.sparta.booker.domain.user.entity.User;
import com.sparta.booker.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MySQLBookService {
	private final BookRepository bookRepository;
	private final BookLikeRepository bookLikeRepository;

	private final UserRepository userRepository;
	private final RedisUtil redisUtil;
		// 책 리스트 가져오기/페이지네이션
		public BookListDto getBookList(Pageable pageable){
			return new BookListDto(bookRepository.findAll(pageable).stream().map(BookDto::new).collect(Collectors.toList()),
				Long.parseLong(redisUtil.get("Count").toString()));
		}

		public List<BookDto> searchFileterKeyword(Pageable pageable, String keyword){
			return bookRepository.findByBookNameContainingOrAuthorContainingOrderByInsertionTimeAsc(pageable,keyword,keyword).stream().map(BookDto::new).collect(
				Collectors.toList());
	}

	// 모든 책 검색하기(필터링추가)/좋아요순서
	public List<BookDto> searchfileter(Pageable pageable, String keyword, String order){
		List<BookDto> bookDtoList = new ArrayList<>();
		if(order.equals("ASC") || order.equals("asc")){
			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByLikeCountAsc(pageable,keyword,keyword).stream().map(BookDto::new).collect(
				Collectors.toList());
		}else {
			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByLikeCountDesc(pageable,keyword,keyword).stream()
				.map(BookDto::new).collect(Collectors.toList());
		}
		return bookDtoList;
	}

	// 모든 책 검색하기(필터링추가)/별점순서
	public List<BookDto> searchFileterRating(Pageable pageable, String keyword, String order){
		List<BookDto> bookDtoList = new ArrayList<>();
		if(order.equals("ASC") || order.equals("asc")){
			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByStarAsc(pageable,keyword,keyword).stream().map(BookDto::new).collect(
				Collectors.toList());
		}else{
			bookDtoList = bookRepository.findByBookNameContainingOrAuthorContainingOrderByStarDesc(pageable,keyword,keyword).stream().map(BookDto::new).collect(
				Collectors.toList());
		}
		return bookDtoList;
	}

	//좋아요 카운트 수정
	@Transactional
	public LikeDto likeCount(Long bookid, User user) {
			Book findbook = bookRepository.findById(bookid).orElseThrow(() -> {
				return new IllegalArgumentException("존재하지 않는 책입니다.");
			});
			Optional<BookLike> bookLike = Optional.ofNullable(
				bookLikeRepository.findByUserIdAndBookId(user.getUserId(), bookid));
			if(!bookLike.isPresent()){
				bookLikeRepository.save(new BookLike(user.getUserId(), bookid));
				findbook.upLikeCount();
			}
			return new LikeDto(findbook.getId(), findbook.getLikeCount());
	}
}
