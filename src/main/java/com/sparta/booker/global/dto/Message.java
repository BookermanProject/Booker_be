package com.sparta.booker.global.dto;

import com.sparta.booker.global.exception.ErrorCode;
import com.sparta.booker.global.exception.SuccessCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class Message {
	private final String msg;
	private final int statusCode;
	private final Object data;

	public static ResponseEntity<Message> toResponseEntity(ErrorCode errorCode) {
		return ResponseEntity
			.status(errorCode.getHttpStatus())
			.header("Content-Type", "application/json;charset=UTF-8")
			.body(Message.builder()
				.msg(errorCode.getDetail())
				.statusCode(errorCode.getHttpStatus().value())
				.build());
	}

	public static ResponseEntity<Message> toResponseEntity(SuccessCode successCode, Object data) {
		return ResponseEntity
			.status(successCode.getHttpStatus())
			.body(Message.builder()
			.msg(successCode.getDetail())
			.statusCode(successCode.getHttpStatus().value())
			.data(data)
			.build());
	}
}