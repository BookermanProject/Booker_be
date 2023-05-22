package com.sparta.booker.controller;

import com.sparta.booker.exception.Message;
import com.sparta.booker.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/book")
public class BookController {
    private final BookService bookService;
    @PostMapping("/excel")
    public ResponseEntity<Message> importExcel (@RequestPart("excel") MultipartFile excel) {
        return bookService.importExcel(excel);
    }
}
