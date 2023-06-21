package com.sparta.booker.domain.search.elastic.service;


import com.sparta.booker.domain.search.elastic.dto.BookDto;
import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.BookListDto;
import com.sparta.booker.domain.search.elastic.repository.BookElasticOperation;
import com.sparta.booker.domain.search.elastic.util.EsDtoConverter;
import com.sparta.booker.domain.search.querydsl.util.RedisUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ElasticSearchBookService {
	private final BookElasticOperation bookElasticOperation;
	private final EsDtoConverter esDtoConverter;
	private final RedisUtil redisUtil;

	public BookListDto searcByElastic(Pageable pageable){
		SearchHits<BookDto> searchHits = bookElasticOperation.SearchByElastic(pageable);
		return esDtoConverter.resultToDto(searchHits, pageable);
	}

	//전체 검색
	public BookListDto searchWordByElastic(@NotNull BookFilterDto bookFilterDto, Pageable pageable) {
		redisUtil.upKeywordCount(bookFilterDto.getQuery());
		SearchHits<BookDto> searchHits = bookElasticOperation.keywordSearchByElastic(bookFilterDto, pageable);
		return esDtoConverter.resultToDto(searchHits, pageable);
	}

	//자동완성
	public List<String> autoMaker(String query){
		return bookElasticOperation.autoMakerDtoSearchHits(query).stream()
			.map(i -> i.getContent().getBook_name()).collect(Collectors.toList());
	}

	public List<String> getTopKeywords(){
		return redisUtil.SearchRankList();
	}

}
