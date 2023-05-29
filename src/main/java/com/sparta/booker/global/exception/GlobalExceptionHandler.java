package com.sparta.booker.global.exception;

import com.sparta.booker.global.dto.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = {CustomException.class})
	protected ResponseEntity<Message> handleCustomException(CustomException e) {
		log.error("handleDataException throw Exception : {}", e.getErrorCode());
		return Message.toResponseEntity(e.getErrorCode());
	}
}