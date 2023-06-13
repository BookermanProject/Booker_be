package com.sparta.booker.domain.search.elastic.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.booker.domain.search.elastic.repository.PerformanceSearchUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/elastic")
public class ElasticSearchBookController {

	// private final ElasticSearchBookService elasticSearchBookService;
//	private  final PerformanceSearchUseCase performanceSearchUseCase;
//
//	@PostMapping("/documents")
//	public String saveDocuments() {
//		performanceSearchUseCase.saveAllDocuments();
//		return "성공";
//	}

	// //필터검색
	// @PostMapping("/search/Word")
	// public ResponseEntity<Message> searchWord(@RequestBody BookFilterDto filterDto) {
	// 	filterDto.checkParameterValid();
	// 	//         BookListDto result = bookService.searchWordByElastic(filterDto);
	// 	return elasticSearchlBookService.searchWordByElastic(filterDto);
	// }

	//단어로 검색
	//    @PostMapping("/search/Filter")
	//    public ResponseEntity<Message> searchFilter(@RequestBody BookDto bookRequestDto) {
	//        return elasticSearchlBookService.searchFilterByElastic(bookRequestDto);
	//    }
	//
	//    @PostMapping("/search/Filter")
	//    public ResponseEntity<Message> searchFilter(@RequestBody BookDto bookRequestDto) {
	//        return elasticSearchlBookService.searchFilterByElastic(bookRequestDto);
	//    }


}