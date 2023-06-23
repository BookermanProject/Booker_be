package com.sparta.booker.domain.search.elastic.util;

	import java.util.List;
	import java.util.stream.Collectors;

	import org.springframework.data.domain.Pageable;
	import org.springframework.data.elasticsearch.core.SearchHit;
	import org.springframework.data.elasticsearch.core.SearchHits;
	import org.springframework.stereotype.Component;

	import com.sparta.booker.domain.search.elastic.dto.BookDto;
	import com.sparta.booker.domain.search.elastic.dto.BookListDto;

@Component
public class EsDtoConverter {
	public BookListDto resultToDto(SearchHits<BookDto> search, Pageable page) {
		List<SearchHit<BookDto>> searchHits = search.getSearchHits();
		List<BookDto> bookDtoList = searchHits.stream().map(hit -> hit.getContent()).collect(Collectors.toList());
		System.out.println(bookDtoList.get(1).getBook_name());
		return new BookListDto(bookDtoList, search.getTotalHits());
	}
}
