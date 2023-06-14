package com.sparta.booker.domain.search.elastic.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.booker.domain.search.elastic.document.BookDocument;
import com.sparta.booker.domain.search.elastic.dto.BookDto;
import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.BookListDto;
import com.sparta.booker.domain.search.elastic.repository.BookElasticOperation;
import com.sparta.booker.global.dto.Message;
import com.sparta.booker.global.exception.SuccessCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticSearchBookService {	private final BookElasticOperation bookElasticOperation;


	//전체 검색
	@Transactional(readOnly = true)
	//    @CircuitBreaker(name = "ElasticError", fallbackMethod = "keywordSearchBySql")
	public ResponseEntity<Message> searchWordByElastic(BookFilterDto bookFilterDto, Pageable pageable) {
		SearchHits<BookDocument> searchHits = bookElasticOperation.keywordSearchByElastic(bookFilterDto, pageable);
		BookListDto bookListDto = resultToDto(searchHits, bookFilterDto, pageable);
		return Message.toResponseEntity(SuccessCode.SEARCH_SUCCESS, bookListDto);
	}

	public ResponseEntity<Message> searchFilterByElastic(BookDto bookRequestDto) {
		return Message.toResponseEntity(SuccessCode.SEARCH_SUCCESS, "test" );
	}

	private BookListDto resultToDto(SearchHits<BookDocument> search, BookFilterDto bookFilterDto, Pageable page) {
		List<SearchHit<BookDocument>> searchHits = search.getSearchHits();
		List<BookDocument> bookDtoList = searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
		List<Object> searchAfter = setSearchAfter(searchHits, bookFilterDto);
		String searchAfterSort = String.valueOf(searchAfter.get(0));
		Long searchAfterId = Long.parseLong(String.valueOf(searchAfter.get(1)));
		return new BookListDto(bookDtoList, searchAfterSort, searchAfterId, page.getPageNumber(), true);
	}

	private List<Object> setSearchAfter(List<SearchHit<BookDocument>> searchHits, BookFilterDto bookFilterDto) {
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
