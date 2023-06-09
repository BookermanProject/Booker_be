package com.sparta.booker.domain.search.elastic.service;


import com.sparta.booker.domain.search.elastic.dto.BookDto;
import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.BookListDto;
import com.sparta.booker.domain.search.elastic.repository.BookElasticOperation;
import com.sparta.booker.domain.search.elastic.util.EsDtoConverter;
import com.sparta.booker.domain.search.querydsl.dto.LikeBookDto;
import com.sparta.booker.domain.search.querydsl.dto.RankBookDto;
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

	public BookListDto searchByElastic(Pageable pageable){
		SearchHits<BookDto> searchHits = bookElasticOperation.SearchByElastic(pageable);

		return esDtoConverter.resultToDto(searchHits, pageable);
	}

	//전체 검색
	public BookListDto searchWordByElastic(@NotNull BookFilterDto bookFilterDto, Pageable pageable) {
		SearchHits<BookDto> searchHits = bookElasticOperation.keywordSearchByElastic(bookFilterDto, pageable);
		return esDtoConverter.resultToDto(searchHits, pageable);
	}

	// 검색어 올리기
	public void searchupCount(String query){
		redisUtil.upKeywordCount(query);
	}

	//자동완성
	public List<String> autoMaker(String query){
		return bookElasticOperation.autoMakerDtoSearchHits(query).stream()
			.map(i -> i.getContent().getBook_name()).collect(Collectors.toList());
	}

	// 탑 10 키워드
	public List<RankBookDto> getSearchTop(){
		return redisUtil.SearchRankList();
	}

	// 좋아요 탑 10
	public List<LikeBookDto> getLikeTop(){
		return redisUtil.SearchList();
	}


}
