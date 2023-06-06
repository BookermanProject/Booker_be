package com.sparta.booker.domain.event.repository;

import com.sparta.booker.domain.event.entity.EventRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {
}
