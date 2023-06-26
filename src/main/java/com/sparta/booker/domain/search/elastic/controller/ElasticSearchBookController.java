package com.sparta.booker.domain.search.elastic.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.BookListDto;
import com.sparta.booker.domain.search.elastic.service.ElasticSearchBookService;
import com.sparta.booker.domain.search.querydsl.dto.LikeBookDto;
import com.sparta.booker.domain.search.querydsl.dto.RankBookDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/elastic")
public class ElasticSearchBookController {

	private final ElasticSearchBookService elasticSearchBookService;

	//기본검색
	@GetMapping("/search")
	@ResponseBody
	public BookListDto search(Pageable pageable) {

		return elasticSearchBookService.searchByElastic(pageable);
	}
	//필터검색
	@GetMapping("/search/word")
	@ResponseBody
	public BookListDto searchWord(BookFilterDto filterDto , Pageable pageable) {
		return elasticSearchBookService.searchWordByElastic(filterDto, pageable);
	}

	// 검색어 카운트
	@PutMapping("/search/count")
	public void serarchupCount(@RequestBody BookFilterDto filterDto) {
		elasticSearchBookService.searchupCount(filterDto.getQuery());
	}
	//자동완성
	@GetMapping("/automaker")
	public List<String> autoMaker(String query) {
		return elasticSearchBookService.autoMaker(query);
	}

	@GetMapping("/searchtop")
	public List<RankBookDto> searchTopKeyword() {
		return elasticSearchBookService.getSearchTop();
	}

	@GetMapping("/liketop")
	public List<LikeBookDto> likeTopKeyword() {
		return elasticSearchBookService.getLikeTop();
	}

}