package com.sparta.booker.domain.search.elastic.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
}