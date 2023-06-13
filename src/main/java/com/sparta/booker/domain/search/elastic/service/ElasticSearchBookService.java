package com.sparta.booker.domain.search.elastic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.BookListDto;

import com.sparta.booker.domain.search.elastic.repository.BookElasticOperation;
import com.sparta.booker.domain.search.querydsl.dto.BookDto;
import com.sparta.booker.global.dto.Message;
import com.sparta.booker.global.exception.SuccessCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticSearchBookService {
	private final BookElasticOperation bookElasticOperation;

	@Transactional(readOnly = true)
	//    @CircuitBreaker(name = "ElasticError", fallbackMethod = "keywordSearchBySql")
	public ResponseEntity<Message> searchWordByElastic(BookFilterDto bookFilterDto) {
		//엘라스틱서치 검색결과를 해당클래스로 원하는 데이터타입으로 가져올 수 있음
		SearchHits<BookDto> searchHits = bookElasticOperation.keywordSearchByElastic(bookFilterDto);
		BookListDto bookListDto = resultToDto(searchHits, bookFilterDto);


		return Message.toResponseEntity(SuccessCode.SEARCH_SUCCESS, bookListDto);
	}

	public ResponseEntity<Message> searchFilterByElastic(BookDto bookRequestDto) {

		return Message.toResponseEntity(SuccessCode.SEARCH_SUCCESS, "test" );
	}

	private BookListDto resultToDto(SearchHits<BookDto> search, BookFilterDto bookFilterDto) {
		List<SearchHit<BookDto>> searchHits = search.getSearchHits();
		System.out.println(search.getTotalHits());
		List<BookDto> bookDtoList = searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());

		List<Object> searchAfter = setSearchAfter(searchHits, bookFilterDto);
		String searchAfterSort = String.valueOf(searchAfter.get(0));
		Long searchAfterId = Long.parseLong(String.valueOf(searchAfter.get(1)));

		return new BookListDto(bookDtoList, searchAfterSort, searchAfterId, bookFilterDto.getPage(), true);
	}

	private List<Object> setSearchAfter(List<SearchHit<BookDto>> searchHits, BookFilterDto bookFilterDto) {
		List<Object> lists = new ArrayList<>();
		if (searchHits.size() == 0) {		// 검색 결과가 없는 경우
			lists.add("-1");
			lists.add("-1");
			return lists;
		} else if (searchHits.size() < bookFilterDto.getTotalRow()) {		// 검색 결과가 총 개수보다 작은 경우
			lists.add("0");
			lists.add("-1");
			return lists;
		}
		return searchHits.get(searchHits.size() - 1).getSortValues();
	}

	//    @Transactional(readOnly = true)
	//    public void keywordSearchBySql(BookFilterDto filter, Throwable t) {
	//        log.warn("keyword Elastic Down : " + t.getMessage());
	//    }

}
