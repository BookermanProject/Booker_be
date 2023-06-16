package com.sparta.booker.domain.search.elastic.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sparta.booker.domain.search.elastic.dto.BookDto;
import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.BookListDto;
import com.sparta.booker.domain.search.elastic.dto.autoMakerDto;
import com.sparta.booker.domain.search.elastic.repository.BookElasticOperation;
import com.sparta.booker.domain.search.elastic.util.EsDtoConverter;
import com.sparta.booker.global.dto.Message;
import com.sparta.booker.global.exception.SuccessCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElasticSearchBookService {
	private final BookElasticOperation bookElasticOperation;
	private final EsDtoConverter esDtoConverter;

	//전체 검색
	@Transactional(readOnly = true)
	public ResponseEntity<Message> searchWordByElastic(BookFilterDto bookFilterDto, Pageable pageable) {

		SearchHits<BookDto> searchHits = bookElasticOperation.keywordSearchByElastic(bookFilterDto, pageable);
		BookListDto bookListDto = esDtoConverter.resultToDto(searchHits, pageable);
		return Message.toResponseEntity(SuccessCode.SEARCH_SUCCESS, bookListDto);
	}



}
