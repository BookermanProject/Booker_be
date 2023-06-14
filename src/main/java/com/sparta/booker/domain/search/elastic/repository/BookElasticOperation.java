package com.sparta.booker.domain.search.elastic.repository;

import com.sparta.booker.domain.search.elastic.document.BookDocument;
import com.sparta.booker.domain.search.elastic.dto.BookDto;
import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.autoMakerDto;
import com.sparta.booker.domain.search.elastic.custom.CustomBoolQueryBuilder;

import org.elasticsearch.index.query.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.sparta.booker.domain.search.elastic.custom.CustomQueryBuilders.matchPhraseQuery;
import static com.sparta.booker.domain.search.elastic.custom.CustomQueryBuilders.sortQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;

@Repository
@RequiredArgsConstructor
@Getter
public class BookElasticOperation {

	private final ElasticsearchOperations operations;

	/*
	SearchHits : ES 에서 검색 결과를 나타내는 클래스
	 */

	// keyword 검색
	public SearchHits<BookDocument> keywordSearchByElastic(BookFilterDto bookFilterDto, Pageable pageable) {
		MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(bookFilterDto.getQuery(),  "bookame", "author");
		NativeSearchQuery build = new NativeSearchQueryBuilder()
			.withQuery(multiMatchQueryBuilder)
			.withPageable(pageable)
			.withSorts(sortQuery(bookFilterDto.getSortCategory(), bookFilterDto.getSort()))
			.build();
		return operations.search(build, BookDocument.class);
	}

	// filter 검색
	public SearchHits<BookDto> filterSearchByElastic(BookFilterDto bookFilterDto, List<Object> searchAfter) {
		NativeSearchQuery build = new NativeSearchQueryBuilder()
			.withMinScore(50f)
			.withQuery(new CustomBoolQueryBuilder()
				.must(multiMatchQuery(bookFilterDto.getQuery(), "bookName", "author"))
				.should(matchPhraseQuery("book_name", bookFilterDto.getQuery()))
				.must(matchQuery("author", bookFilterDto.getAuthor()))
				.must(matchQuery("publisher", bookFilterDto.getPublisher())))
			.withSearchAfter(searchAfter)
			.withSorts(sortQuery(bookFilterDto.getSortCategory(), bookFilterDto.getSort()))
			.build();
		return operations.search(build, BookDto.class);
	}

	// 자동 완성
	public SearchHits<autoMakerDto> autoMaker(String query) {
		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
			.withQuery(new BoolQueryBuilder()
				.should(new MatchPhraseQueryBuilder("bookName", query))
				.should(new PrefixQueryBuilder("bookName.keyword", query))
			)
			.withCollapseField("bookName.keyword")
			.build();
		return operations.search(searchQuery, autoMakerDto.class);
	}
}
