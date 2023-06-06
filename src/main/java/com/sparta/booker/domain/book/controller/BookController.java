package com.sparta.booker.domain.book.controller;

import com.sparta.booker.domain.book.dto.BookFilterDto;
import com.sparta.booker.domain.book.dto.BookDto;
import com.sparta.booker.global.dto.Message;
import com.sparta.booker.domain.book.service.BookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
@Slf4j
public class BookController {
    private final BookService bookService;
    // @PostMapping("/excel")
    // public ResponseEntity<Message> importExcel (@RequestPart("excel") MultipartFile excel) {
    //     return bookService.importExcel(excel);
    // }

    //필터검색
    @PostMapping("/search/Word")
    public ResponseEntity<Message> searchWord (@RequestBody BookFilterDto filterDto) {
        filterDto.checkParameterValid();
//         BookListDto result = bookService.searchWordByElastic(filterDto);
         return bookService.searchWordByElastic(filterDto);
    }

    //단어로 검색
    @PostMapping("/search/Filter")
    public ResponseEntity<Message> searchFilter (@RequestBody BookDto bookRequestDto) {
        return bookService.searchFilterByElastic(bookRequestDto);
    }
}
