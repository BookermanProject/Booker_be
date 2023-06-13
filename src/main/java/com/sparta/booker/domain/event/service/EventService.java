package com.sparta.booker.domain.event.service;

import com.sparta.booker.domain.search.querydsl.entity.Book;
import com.sparta.booker.domain.search.querydsl.repository.BookRepository;
import com.sparta.booker.domain.event.dto.EventRequestDto;
import com.sparta.booker.domain.event.entity.Event;
import com.sparta.booker.domain.event.repository.EventRepository;
import com.sparta.booker.domain.user.dto.ResponseDto;
import com.sparta.booker.domain.user.entity.User;
import com.sparta.booker.domain.user.entity.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final BookRepository bookRepository;

    // 이벤트 등록
    public ResponseEntity<ResponseDto> createEvent(EventRequestDto eventRequestDto, User user) {

        //이벤트에 등록하려는 책이 book 테이블에 있는지 체크
        Book book = bookRepository.findById(eventRequestDto.getBookId()).orElseThrow(
                () -> new NullPointerException("해당 책이 존재하지 않습니다")
        );

        //로그인 한 사용자가 관리자인지 체크
        if(UserRole.ADMIN.equals(user.getRole())) {
            Event event = eventRepository.save(new Event(eventRequestDto, book));
        } else {
            throw new IllegalArgumentException("관리자만 등록 가능합니다.");
        }
        return ResponseEntity.ok().body(new ResponseDto("이벤트 등록 성공"));
    }




}
