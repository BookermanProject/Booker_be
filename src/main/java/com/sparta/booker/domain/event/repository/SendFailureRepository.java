package com.sparta.booker.domain.event.repository;

import com.sparta.booker.domain.event.document.sendFailure;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface SendFailureRepository extends ElasticsearchRepository<sendFailure, Long> {
    List<sendFailure> findByEventId(Long eventId);
}
