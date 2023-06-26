package com.sparta.booker.domain.event.controller;

import com.sparta.booker.domain.event.dto.*;
import com.sparta.booker.domain.event.service.EventService;
import com.sparta.booker.domain.user.dto.ResponseDto;
import com.sparta.booker.domain.user.entity.User;
import com.sparta.booker.domain.kafka.service.KafkaProducer;
import com.sparta.booker.domain.user.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;
    private final KafkaProducer kafkaProducer;

    //이벤트 등록 API
    @PostMapping("/event")
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto, @AuthenticationPrincipal UserDetailsImpl user) {
        return eventService.createEvent(eventRequestDto, user.getUser());
    }

    //이벤트 신청 API
    @PostMapping("/event/{eventId}")
    public ResponseEntity<EventResponseDto> applyEvent(@PathVariable Long eventId, @AuthenticationPrincipal UserDetailsImpl user) {
        return kafkaProducer.produceMessage(eventId, user.getUser());
        //return ResponseEntity.ok().body(new ResponseDto("이벤트 신청 완료"));
    }

    @PostMapping("/event/batch")
    public ResponseEntity<ResponseDto> applyBatchEvent(@RequestBody BatchDto batchDto, @AuthenticationPrincipal UserDetailsImpl user) {
        kafkaProducer.produceMessage_Batch(batchDto, user.getUser());
        return ResponseEntity.ok().body(new ResponseDto("이벤트 신청 완료"));
    }

    @GetMapping("/event/getEventDat")
    public ResponseEntity<EventDateDto> getEventDat() {
        return eventService.getEventDatList();
    }

    @GetMapping("/event/getPreEventList")
    public ResponseEntity<EventSearchDto> getPreEventList(String searchDat) {
        return eventService.getPreEventList(searchDat);
    }

    @GetMapping("/event/searchDat")
    public ResponseEntity<EventDateDto> getSearchDat() {
        return eventService.getSearchDatList();
    }
}
