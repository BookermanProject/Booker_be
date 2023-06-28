package com.sparta.booker.domain.event.repository;

import com.sparta.booker.domain.event.entity.EventRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {
    List<EventRequest> findByUserId(String userId);
}
