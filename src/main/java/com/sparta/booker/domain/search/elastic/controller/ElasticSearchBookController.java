package com.sparta.booker.domain.search.elastic.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.service.ElasticSearchBookService;
import com.sparta.booker.global.dto.Message;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/elastic")
public class ElasticSearchBookController {

	private final ElasticSearchBookService elasticSearchBookService;

	//필터검색
	@PostMapping("/search/word")
	public ResponseEntity<Message> searchWord(@RequestBody BookFilterDto filterDto, Pageable pageable) {
		return elasticSearchBookService.searchWordByElastic(filterDto, pageable);
	}

	//자동완성
	@GetMapping("/automaker")
	public List<String> autoMaker(String query) {
		return elasticSearchBookService.autoMaker(query);
	}

	//실시간 검색어
	@GetMapping("/books/key")
	public List<String> realtiemkeyword(){
		return elasticSearchBookService.realtiemkeyword();
	}
}