package com.sparta.booker.domain.book.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.domain.book.repository.DSLBookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QueryDslBookService {
	private final DSLBookRepository dslBookRepository;

	// 책 리스트 가져오기/페이지네이션
	public List<BookDto> getBookList(String keyword,Pageable pageable, String category, String order){
		return dslBookRepository.findByBookList(keyword,pageable,category,order).stream().map(BookDto::new).collect(Collectors.toList());
	}


}
