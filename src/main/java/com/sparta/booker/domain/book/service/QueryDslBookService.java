package com.sparta.booker.domain.book.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.book.repository.IdxBookRepository;
import com.sparta.booker.domain.book.repository.DSLBookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryDslBookService {
	private final DSLBookRepository DSLBookRepository;

	private final IdxBookRepository idxBookRepository;

	// 책 리스트 가져오기/페이지네이션
	public List<BookDto> getBookList(String keyword,Pageable pageable, String category, String order){
		return DSLBookRepository.findByBookList(keyword,pageable,category,order).stream().map(BookDto::new).collect(Collectors.toList());
	}

	//Fulltext index like
	public List<BookDto> getBookListbyFullTextLike(String keyword,Pageable pageable, String order){
		List<BookDto> bookDtoList = new ArrayList<>();
		if(order.equals("DESC") || order.equals("desc")){
			return bookDtoList = idxBookRepository.findByBookListbyFullTextLikedesc(keyword, pageable).stream().map(BookDto::new).collect(Collectors.toList());
		}
		return idxBookRepository.findByBookListbyFullTextLikeasc(keyword,pageable).stream().map(BookDto::new).collect(Collectors.toList());
	}

	// Fulltext index star
	public List<BookDto> getBookListbyFullTextStar(String keyword,Pageable pageable, String order){
		List<BookDto> bookDtoList = new ArrayList<>();
		if(order.equals("DESC") || order.equals("desc")){
			return bookDtoList = idxBookRepository.findByBookListbyFullTextStardesc(keyword, pageable).stream().map(BookDto::new).collect(Collectors.toList());
		}
		return idxBookRepository.findByBookListbyFullTextStarasc(keyword,pageable).stream().map(BookDto::new).collect(Collectors.toList());
	}

	//convering index
	public List<BookDto> getBookListbyCoverIdx(String keyword,Pageable pageable, String category, String order){
		return DSLBookRepository.findByBookListbyCovering(keyword,pageable,category,order).stream().map(BookDto::new).collect(Collectors.toList());
	}

}
