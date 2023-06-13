package com.sparta.booker.domain.search.querydsl.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class LikeDto {
	private Long id;
	private int like_count;

	public LikeDto(Long id, int like_count){
		this.id = id;
		this.like_count = like_count;
	}


}
