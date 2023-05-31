package com.sparta.booker.domain.event.controller;

import com.sparta.booker.domain.event.dto.EventRequestDto;
import com.sparta.booker.domain.event.service.EventService;
import com.sparta.booker.domain.user.dto.ResponseDto;
import com.sparta.booker.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    //이벤트 등록 API
    @PostMapping("/event")
    public ResponseEntity<ResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto, @AuthenticationPrincipal User user) {
        return eventService.createEvent(eventRequestDto, user);
    }




}
