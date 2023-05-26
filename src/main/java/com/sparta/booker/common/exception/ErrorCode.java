package com.sparta.booker.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	/* 400 BAD_REQUEST : 잘못된 요청 */
	INVALID_PARAMETERS(BAD_REQUEST, "잘못된 URL입니다."),

	/* 403 Forbidden : 권한 없음 */
	Forbidden(FORBIDDEN, "권한이 없습니다"),

	/* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
	BOOK_NOT_FOUND(NOT_FOUND, "해당 책 정보를 찾을 수 없습니다"),
	QUERY_NOT_FOUND(NOT_FOUND, "검색어를 찾을 수 없습니다."),

	/* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
	DUPLICATE_RESOURCE(CONFLICT, "데이터가 이미 존재합니다"),
	CONFLICT_KEY(CONFLICT, "키를 점거할 수 없습니다.");

	private final HttpStatus httpStatus;
	private final String detail;
}


