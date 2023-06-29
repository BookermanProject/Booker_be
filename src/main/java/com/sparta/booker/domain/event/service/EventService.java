package com.sparta.booker.domain.event.service;


import com.sparta.booker.domain.event.document.sendFailure;
import com.sparta.booker.domain.event.dto.EventResponseDto;
import com.sparta.booker.domain.event.dto.*;
import com.sparta.booker.domain.event.entity.EventRequest;
import com.sparta.booker.domain.event.repository.EventRequestRepository;
import com.sparta.booker.domain.event.repository.SendFailureRepository;
import com.sparta.booker.domain.search.querydsl.entity.Book;
import com.sparta.booker.domain.search.querydsl.repository.BookRepository;
import com.sparta.booker.domain.event.entity.Event;
import com.sparta.booker.domain.event.repository.EventRepository;
import com.sparta.booker.domain.user.entity.User;
import com.sparta.booker.domain.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final BookRepository bookRepository;
    private final EventRequestRepository eventRequestRepository;
    private final SendFailureRepository sendFailureRepository;

    // 이벤트 등록
    public ResponseEntity<EventResponseDto> createEvent(EventRequestDto eventRequestDto, User user) {

        //이벤트에 등록하려는 책이 book 테이블에 있는지 체크
        Book book = bookRepository.findById(eventRequestDto.getBookId()).orElseThrow(
                () -> new NullPointerException("해당 책이 존재하지 않습니다")
        );

        //로그인 한 사용자가 관리자인지 체크
        if(UserRole.ADMIN.equals(user.getRole())) {
            Event event = eventRepository.save(new Event(eventRequestDto, book.getId()));
        } else {
            throw new IllegalArgumentException("관리자만 등록 가능합니다.");
        }
        return ResponseEntity.ok().body(new EventResponseDto("이벤트 등록 성공"));
    }

    //성공 이벤트 조회
    public ResponseEntity<EventSuccessResponseDto> getSuccessEventList(String userId) {
        List<EventRequest> eventRequests = eventRequestRepository.findByUserId(userId);
        if (!eventRequests.isEmpty()) {
            EventRequest eventRequest = eventRequests.get(0); // 여러 개의 이벤트 중에서 첫 번째 이벤트 선택
            EventSuccessResponseDto eventSuccessDto = new EventSuccessResponseDto(
                    eventRequest.getEventId(),
                    eventRequest.getEventDate(),
                    eventRequest.getEventTime()
            );
            return ResponseEntity.ok(eventSuccessDto);
        } else {
            // 이벤트 신청 정보가 없을 경우 처리
            return ResponseEntity.notFound().build();
        }
    }

    //실패 이벤트 조회
    public ResponseEntity<EventFailResponseDto> getFailEventList(String userId) {
        List<sendFailure> failures = sendFailureRepository.findByUserId(userId);
        if (!failures.isEmpty()) {
            sendFailure sf = failures.get(0); // 여러 개의 이벤트 중에서 첫 번째 이벤트 선택
            EventFailResponseDto eventFailResponseDto = new EventFailResponseDto(
                    sf.getEventId(),
                    sf.getEventDate(),
                    sf.getEventTime()
            );
            return ResponseEntity.ok(eventFailResponseDto);
        } else {
            // 이벤트 신청 정보가 없을 경우 처리
            return ResponseEntity.notFound().build();
        }
    }

    //등록된 이벤트 날짜 가져오기
    public ResponseEntity<EventDateDto> getEventDatList() {
        List<Event> eventList = eventRepository.findAll();
        List<String> datList = new ArrayList<>();
        String dat = "";
        for(Event event: eventList) {
            dat = event.getEventDate();
            datList.add(dat);
        }
        List<String> getList = datList.stream().distinct().collect(Collectors.toList());

        return ResponseEntity.ok().body(new EventDateDto(getList));
    }

    //등록된 이벤트 내역 가져오기
    public ResponseEntity<EventSearchDto> getPreEventList(String searchDat) {
        List<Event> eventList = eventRepository.findByEventDate(searchDat);

        List<EventRequestDto> datList = new ArrayList<>();
        for(Event event: eventList) {
            EventRequestDto requestDto = new EventRequestDto(event);
            datList.add(requestDto);
        }

        return ResponseEntity.ok().body(new EventSearchDto(datList));
    }


    //등록된 이벤트 내역 가져오기
    public ResponseEntity<EventDateDto> getSearchDatList() {
        List<Book> bookList = bookRepository.findTop10ByOrderByLikeCountDesc();
        List<String> datList = new ArrayList<>();
        String dat = "";
        for(Book book: bookList) {
            dat = book.getId().toString() + "<>" + book.getBookName();
            datList.add(dat);
        }

        return ResponseEntity.ok().body(new EventDateDto(datList));
    }
}
