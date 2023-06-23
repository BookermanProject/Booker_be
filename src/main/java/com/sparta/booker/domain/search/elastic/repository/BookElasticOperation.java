package com.sparta.booker.domain.search.elastic.repository;

import static com.sparta.booker.domain.search.elastic.custom.CustomQueryBuilders.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;
import com.sparta.booker.domain.search.elastic.dto.BookDto;
import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.autoMakerDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@Getter
public class BookElasticOperation {
	private final ElasticsearchOperations operations;
	/*
	SearchHits : ES 에서 검색 결과를 나타내는 클래스
	 */
	public SearchHits<BookDto> SearchByElastic(Pageable pageable) {

		MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();

		NativeSearchQuery build = new NativeSearchQueryBuilder()
			.withQuery(matchAllQueryBuilder)
			.withPageable(pageable)
			.build();

		return operations.search(build, BookDto.class);

	}

	// keyword 검색
	public SearchHits<BookDto> keywordSearchByElastic(BookFilterDto bookFilterDto, Pageable pageable) {

		MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(bookFilterDto.getQuery(),
			"book_name", "author");

		NativeSearchQuery build = new NativeSearchQueryBuilder()
			.withQuery(multiMatchQueryBuilder)
			.withPageable(pageable)
			.withSorts(sortQuery(bookFilterDto.getSortCategory(), bookFilterDto.getSort()))
			.build();

		return operations.search(build, BookDto.class);
	}

	// 자동 완성 기능
	public SearchHits<autoMakerDto> autoMakerDtoSearchHits(String query) {
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(
				new BoolQueryBuilder()
					.should(new MatchQueryBuilder("book_name", query))
					.should(new PrefixQueryBuilder("book_name.keyword", query))
			)
			.withCollapseField("book_name.keyword")
			.build();
		return operations.search(searchQuery, autoMakerDto.class);
	}
}