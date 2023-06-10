package com.sparta.booker.domain.book.util;

import static com.sparta.booker.domain.book.entity.QBook.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberTemplate;

@Component
public class DslSortCursorUtil {

	public Order orderchose(String order){
		if(order.equals("ASC")){
			return Order.ASC;
		}else{
			return Order.DESC;
		}
	}

	public OrderSpecifier<?>[] getCursorSort(String orderBycategory, String sort) {
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
		if (orderBycategory == null) {
			orderSpecifiers.add(new OrderSpecifier(Order.ASC, book.insertionTime));
			return orderSpecifiers.toArray(OrderSpecifier<?>[]::new);
		}
		if (orderBycategory.equals("Like")) {
			orderSpecifiers.add(new OrderSpecifier(orderchose(sort), book.likeCount));
		} else if (orderBycategory.equals("Star")) {
			orderSpecifiers.add(new OrderSpecifier(orderchose(sort), book.star));
		}
		return orderSpecifiers.toArray(OrderSpecifier<?>[]::new);
	}

	// full-text query
	public NumberTemplate fulltextbyBookName(String query) {
		NumberTemplate template = Expressions.numberTemplate(Double.class,
			"function('match',{0},{1})", book.bookName, query + "*");

		return template;
	}

	public NumberTemplate fulltextbyAuthor(String query) {
		NumberTemplate template = Expressions.numberTemplate(Double.class,
			"function('match',{0},{1})", book.author, query + "*");

		return template;
	}

}
