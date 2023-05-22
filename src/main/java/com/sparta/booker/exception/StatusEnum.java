package com.sparta.booker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum StatusEnum {
    OK(HttpStatus.OK,"OK");
    private final HttpStatus status;
    private final String message;
}
