package com.sparta.booker.domain.event.repository;

import com.sparta.booker.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}
