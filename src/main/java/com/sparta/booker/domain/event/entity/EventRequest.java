package com.sparta.booker.domain.event.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Getter
@NoArgsConstructor
public class EventRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long eventId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String winDate;

    public EventRequest(Long eventId, String userId, String winDate) {
        this.eventId = eventId;
        this.userId = userId;
        this.winDate = winDate;
    }
}
