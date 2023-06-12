package com.sparta.booker.domain.search.elastic.repository;

import com.sparta.booker.domain.search.querydsl.dto.BookDto;
import com.sparta.booker.domain.search.elastic.dto.BookFilterDto;
import com.sparta.booker.domain.search.elastic.dto.autoMakerDto;
import com.sparta.booker.domain.search.elastic.custom.CustomBoolQueryBuilder;

import org.elasticsearch.index.query.*;
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

	// keyword 검색
	public SearchHits<BookDto> keywordSearchByElastic(BookFilterDto bookFilterDto) {
		MultiMatchQueryBuilder multiMatchQueryBuilder = QueryBuilders.multiMatchQuery(bookFilterDto.getQuery(),
			"book_name", "authors");

		NativeSearchQuery build = new NativeSearchQueryBuilder()
			.withQuery(multiMatchQueryBuilder)
			.withSorts(sortQuery(bookFilterDto.getSort()))
			.build();

		return operations.search(build, BookDto.class);
	}

	// filter 검색
	public SearchHits<BookDto> filterSearchByElastic(BookFilterDto bookFilterDto, List<Object> searchAfter) {
		NativeSearchQuery build = new NativeSearchQueryBuilder()
			.withMinScore(50f)
			.withQuery(new CustomBoolQueryBuilder()
				.must(multiMatchQuery(bookFilterDto.getQuery(), "bookName", "authors"))
				//                        .should(new RangeQueryBuilder("inventory").gte(1).boost(40F))
				//                        .mustNot(inventoryQuery(bookFilterDto.getSort()))
				//                        .filter(matchQuery("category.keyword", bookFilterDto.getSort()))
				//                        .filter(matchQuery("baby_category.keyword", bookFilterDto.getBabyCategory()))
				//                        .filter(priceQuery(bookFilterDto.getMinPrice(), bookFilterDto.getMaxPrice()))
				//                        .filter(starQuery(bookFilterDto.getStar()))
				//                        .filter(yearQuery(bookFilterDto.getPublicationYear()))
				.should(matchPhraseQuery("bookName", bookFilterDto.getQuery()))
				.must(matchQuery("authors", bookFilterDto.getAuthors()))
				.must(matchQuery("publisher", bookFilterDto.getPublisher())))
			.withSearchAfter(searchAfter)
			.withSorts(sortQuery(bookFilterDto.getSort()))
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
