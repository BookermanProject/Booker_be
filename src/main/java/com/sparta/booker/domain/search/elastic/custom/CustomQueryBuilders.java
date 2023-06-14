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

