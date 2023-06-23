package com.sparta.booker.domain.search.querydsl.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookListDto {

	private long page;

	private List<BookDto> books;

	public BookListDto(List<BookDto> list, long page){
		this.books = list;
		this.page = page;
	}


}
