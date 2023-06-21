package com.sparta.booker.domain.search.elastic.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.BookListDto;
import com.sparta.booker.domain.search.elastic.service.ElasticSearchBookService;

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
	@ResponseBody
	public BookListDto searchWord(@RequestBody BookFilterDto filterDto , Pageable pageable) {
		return elasticSearchBookService.searchWordByElastic(filterDto, pageable);
	}

	//자동완성
	@GetMapping("/automaker")
	public List<String> autoMaker(String query) {
		return elasticSearchBookService.autoMaker(query);
	}

	@GetMapping("/topkeyword")
	public List<String> topKeyword() {
		return elasticSearchBookService.getTopKeywords();
	}

}