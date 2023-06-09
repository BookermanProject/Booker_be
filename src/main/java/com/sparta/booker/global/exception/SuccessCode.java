package com.sparta.booker.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessCode {
	SEARCH_SUCCESS(HttpStatus.OK, "검색 성공."),
	IMPORT_SUCCESS(HttpStatus.OK, "임포트 성공.");


	private final HttpStatus httpStatus;
	private final String detail;
}
