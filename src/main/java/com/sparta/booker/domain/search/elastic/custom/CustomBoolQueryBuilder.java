package com.sparta.booker.domain.search.elastic.custom;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.stereotype.Component;

/**
 *  null 처리를 위해 새로 만든 클래스. BoolQueryBuilder를 상속받는다.
 *  매개변수로 입력받는 QueryBuilder는 CustomQueryBuilders의 메서드로 입력받는다.
 *  매개변수 QueryBuilder에 null이 입력되면 계속해서 빌더를 이어가기 위해서 객체 자신을 반환한다.
 *  null이 아닌 경우, 원래 메서드(super.~)를 실행하여 필요한 로직을 수행한다.
 */
@Component
public class CustomBoolQueryBuilder extends BoolQueryBuilder {

	@Override
	public BoolQueryBuilder must(QueryBuilder queryBuilder) {
		if (queryBuilder == null)
			return this;
		return super.must(queryBuilder);
	}

	@Override
	public BoolQueryBuilder filter(QueryBuilder queryBuilder) {
		if (queryBuilder == null)
			return this;
		return super.filter(queryBuilder);
	}

	@Override
	public BoolQueryBuilder should(QueryBuilder queryBuilder) {
		if (queryBuilder == null)
			return this;
		return super.should(queryBuilder);
	}

}