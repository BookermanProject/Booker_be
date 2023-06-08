package com.sparta.booker.domain.book.util;

import static com.sparta.booker.domain.book.entity.QBook.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;

@Component
public class DslSortCursor {

	public Order orderchose(String order){
		if(order.equals("ASC")){
			return Order.ASC;
		}else{
			return Order.DESC;
		}
	}

	public OrderSpecifier<?>[] getCursorSort(String category, String sort) {
		List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();
		if (category == null) {
			orderSpecifiers.add(new OrderSpecifier(Order.ASC, book.insertionTime));
			return orderSpecifiers.toArray(OrderSpecifier<?>[]::new);
		}
		if (category.equals("Like")) {
			orderSpecifiers.add(new OrderSpecifier(orderchose(sort), book.likeCount));
		} else if (category.equals("Star")) {
			orderSpecifiers.add(new OrderSpecifier(orderchose(sort), book.star));
		}
		return orderSpecifiers.toArray(OrderSpecifier<?>[]::new);
	}
}
