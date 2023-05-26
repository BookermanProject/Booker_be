package com.sparta.booker.repository;

import com.sparta.booker.dto.BookDto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookElasticRepository  extends ElasticsearchRepository<BookDto, Long> {
}
