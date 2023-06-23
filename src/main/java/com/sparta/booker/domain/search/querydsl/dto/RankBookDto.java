package com.sparta.booker.domain.search.querydsl.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RankBookDto {

	private String bookName;
	private int searchCount;

	public RankBookDto(String bookName, int searchCount) {
		this.bookName = bookName;
		this.searchCount = searchCount;
	}
}
