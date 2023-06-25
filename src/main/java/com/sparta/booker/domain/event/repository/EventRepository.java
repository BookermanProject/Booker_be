package com.sparta.booker.domain.event.repository;

import com.sparta.booker.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEventDate(String eventDate);
}
