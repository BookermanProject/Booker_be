package com.sparta.booker.domain.search.elastic.repository;

import com.sparta.booker.domain.search.querydsl.dto.BookDto;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookElasticRepository extends ElasticsearchRepository<BookDto, Long> {
}
