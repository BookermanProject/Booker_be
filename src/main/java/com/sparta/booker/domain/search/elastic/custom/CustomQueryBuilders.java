package com.sparta.booker.domain.search.elastic.custom;

import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 *  null 처리를 위해 새로 만든 클래스. 기존 클래스는 QueryBuilders이다.
 *  원래는 상속받아서 사용하려고 했지만 QueryBuilders가 final클래스라 필요한 메서드를 복사해서 사용
 *  null인 경우 예외를 반환하는 것이 아닌 null을 반환하도록 변경
 *  반환된 null값은 CustomBoolQueryBuilder에서 처리
 */
@Component
public class CustomQueryBuilders {

	private static final String STAR = "star";
	private static final String PRICE = "price";
	private static final String YEAR = "year";

	public static MatchQueryBuilder matchQuery(String name, String text) {
		if (text == null)
			return null;
		else if (text.isEmpty())
			return null;
		return new MatchQueryBuilder(name, text);
	}

	public static MatchPhraseQueryBuilder matchPhraseQuery(String name, String text) {
		if (text == null)
			return null;
		else if (text.isEmpty())
			return null;
		return new MatchPhraseQueryBuilder(name, text);
	}

	// public static MultiMatchQueryBuilder multiMatchQuery(Object text, String... fieldNames) {
	// 	if (fieldNames.length == 0)
	// 		return null;
	// 	return new MultiMatchQueryBuilder(text, fieldNames); // BOOLEAN is the default
	// }

	// 가격 쿼리 결정
	public static RangeQueryBuilder priceQuery(Integer minPrice, Integer maxPrice) {
		if (minPrice == null && maxPrice == null)    // 둘 다 입력 안함
			return null;
		if (minPrice == null)    // 최대 가격만 입력
			return new RangeQueryBuilder(PRICE).lte(maxPrice);
		else if (maxPrice == null)    // 최소 가격만 입력
			return new RangeQueryBuilder(PRICE).gte(minPrice);
		return new RangeQueryBuilder(PRICE).gte(minPrice).lte(maxPrice);    // 둘 다 입력
	}

	// 발행 년도 쿼리 결정
	public static RangeQueryBuilder yearQuery(Integer year) {
		if (year == null)    // 입력 안한 경우
			return null;
		else if (year == 0) {
			return null;
		}
		if (year == 2020)    // 특수한 입력 (2020 이후, 1899 이전)
			return new RangeQueryBuilder(YEAR).gte(2020);
		else if (year == 1899)
			return new RangeQueryBuilder(YEAR).lte(1899);
		return new RangeQueryBuilder(YEAR).gte(year).lt(year + 10);    // 일반 입력
	}

	// 별점 쿼리 결정
	public static RangeQueryBuilder starQuery(Integer star) {
		if (star == null)    // 입력 안한 경우
			return null;
		if(star == 0)
			return null;
		return new RangeQueryBuilder(STAR).gte(star);    // 입력 한 경우
	}

	//품절 선택 여부
	public static MatchQueryBuilder inventoryQuery (Integer inventory) {
		System.out.println("inventory = " + inventory);
		if(inventory == null)
			return new MatchQueryBuilder("inventory",-1);
		if(inventory == 0)
			return new MatchQueryBuilder("inventory",-1);
		return new MatchQueryBuilder("inventory",0);
	}


	// 정렬 선택 (좋아요, 별점, 기본)
	public static List<SortBuilder<?>> sortQuery(String sortCategory, String sort) {
		List<SortBuilder<?>> sortBuilders = new ArrayList<>();

		if (sort == null) {
			sortBuilders.add(SortBuilders.scoreSort());
			sortBuilders.add(SortBuilders.fieldSort("insertion_time").order(SortOrder.ASC));
		}
		else if (sort.toUpperCase().equals("DESC")) {
			sortBuilders.add(SortBuilders.fieldSort(sortCategory).order(SortOrder.DESC));
		}
		else if (sort.toUpperCase().equals("ASC")) {
			sortBuilders.add(SortBuilders.fieldSort(sortCategory).order(SortOrder.ASC));
		}
		return sortBuilders;
	}
}

