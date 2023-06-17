package com.sparta.booker.domain.event.controller;

import com.sparta.booker.domain.event.dto.BatchDto;
import com.sparta.booker.domain.event.dto.EventRequestDto;
import com.sparta.booker.domain.event.service.EventService;
import com.sparta.booker.domain.user.dto.ResponseDto;
import com.sparta.booker.domain.user.entity.User;
import com.sparta.booker.domain.kafka.service.KafkaProducer;
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
    public ResponseEntity<ResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto, @AuthenticationPrincipal User user) {
        return eventService.createEvent(eventRequestDto, user);
    }

    //이벤트 신청 API
    @PostMapping("/event/{eventId}")
    public ResponseEntity<ResponseDto> applyEvent(@PathVariable Long eventId, @AuthenticationPrincipal User user) {
        kafkaProducer.produceMessage(eventId, user);
        return ResponseEntity.ok().body(new ResponseDto("이벤트 신청 완료"));
    }

    @PostMapping("/event/batch")
    public ResponseEntity<ResponseDto> applyBatchEvent(@RequestBody BatchDto batchDto, @AuthenticationPrincipal User user) {
        kafkaProducer.produceMessage_Batch(batchDto, user);
        return ResponseEntity.ok().body(new ResponseDto("이벤트 신청 완료"));
    }


}
