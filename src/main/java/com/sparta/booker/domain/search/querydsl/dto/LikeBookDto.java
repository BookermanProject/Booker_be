package com.sparta.booker.domain.search.querydsl.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeBookDto {

	private String bookName;
	private int likeCount;

	public LikeBookDto(String bookName, int likeCount){
		this.bookName=bookName;
		this.likeCount=likeCount;
	}
}
