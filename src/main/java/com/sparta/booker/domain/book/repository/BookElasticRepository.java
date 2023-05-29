package com.sparta.booker.domain.book.repository;

import com.sparta.booker.domain.book.dto.BookDto;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;


public interface BookElasticRepository extends ElasticsearchRepository<BookDto, Long> {
}
