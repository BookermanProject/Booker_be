package com.sparta.booker.domain.kafka.service;

import com.sparta.booker.domain.event.document.sendFailure;
import com.sparta.booker.domain.event.dto.BatchDto;
import com.sparta.booker.domain.event.entity.Event;
import com.sparta.booker.domain.event.entity.EventRequest;
import com.sparta.booker.domain.event.repository.EventRepository;
import com.sparta.booker.domain.event.repository.EventRequestRepository;
import com.sparta.booker.domain.event.repository.SendFailureRepository;
import com.sparta.booker.domain.kafka.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class KafkaThreadConsumer {

    private final EventRepository eventRepository;
    private final EventRequestRepository eventRequestRepository;
    private final SendFailureRepository sendFailureRepository;

    private void processMessage_Batch(Long bookId, Long eventId, String userId, String eventDate, String eventTime) {
        log.info("Received Message : {}", bookId, eventId, eventDate, eventTime);

        // Event 메시지 파싱
        try {
            // 이벤트 성공, 실패 여부 판단
            // book_cnt가 0 보다 클 경우에만 성공으로 처리
            Event event = eventRepository.findById(eventId).orElseThrow(
                    () -> new IllegalArgumentException("해당 이벤트는 존재하지 않습니다")
            );

            Long bookCnt = event.getBookCnt();
            boolean isSuccess = bookCnt > 0;

            // 이벤트 결과 메시지 전송
            if (isSuccess) {
                sendSuccessMessage(eventId, userId, eventDate, eventTime);
                bookCnt--;

                //book_cnt 업데이트
                event.update(bookCnt);
//				eventRepository.save(event);
                if (bookCnt == 0) {
                    event.setReason("책이 모두 소진되었습니다");
                }
            } else {
                sendFailureMessage(eventId, userId, eventDate, eventTime);
            }
        } catch (Exception e) {
            log.error("Error parsing event message: {}", e.getMessage());
        }
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "book", partitions = {"0", "1", "2", "3", "4"}), groupId = KafkaProperties.CONSUMER_GROUP_ID, containerFactory = "batchKafkaListenerContainerFactory")
    public void batchProcessOne(List<BatchDto> record) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        record.forEach(batchDto -> processMessage_Batch(batchDto.getBookId(), batchDto.getEventId(), batchDto.getUserId(), batchDto.getEventDate(),  batchDto.getEventTime()));
    }

    @KafkaListener(topicPartitions = @TopicPartition(topic = "book", partitions = {"5", "6", "7", "8", "9"}), groupId = KafkaProperties.CONSUMER_GROUP_ID, containerFactory = "batchKafkaListenerContainerFactory")
    public void batchProcessTwo(List<BatchDto> record) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        record.forEach(batchDto -> processMessage_Batch(batchDto.getBookId(), batchDto.getEventId(), batchDto.getUserId(), batchDto.getEventDate(), batchDto.getEventTime()));
    }

    public void sendSuccessMessage(Long eventId, String userId, String eventDate, String eventTime) {
        log.info("Event ID : {}, User ID : {}, Date : {}, Time : {} - 이벤트 신청 성공", eventId, userId, eventDate, eventTime);
        eventRequestRepository.save(new EventRequest(eventId, userId, eventDate, eventTime));
    }

    public void sendFailureMessage(Long eventId, String userId, String eventDate, String eventTime) {
        log.info("Event ID : {}, User ID : {}, Time : {} - 이벤트 신청 실패", eventId, userId, eventDate, eventTime);
        // (실패한 경우) 해당 이벤트 메시지 elasticsearch에 저장하는 로직
        sendFailure sendFailure = new sendFailure(eventId, userId, eventDate, eventTime);
        sendFailureRepository.save(sendFailure);
    }
}
