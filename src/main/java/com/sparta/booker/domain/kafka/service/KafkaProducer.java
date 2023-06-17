package com.sparta.booker.domain.kafka.service;

import com.sparta.booker.domain.event.dto.BatchDto;
import com.sparta.booker.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

	private int runningId = 0;

	// Kafka 메시지를 전송하기 위한 Spring Kafka의 KafkaTemplate 객체
	// 이 객체를 통해 메시지를 생성하고 전송할 수 있다
	private final KafkaTemplate<Long, String> kafkaTemplate;
	private final KafkaTemplate<Long, BatchDto> kafkaBatchTemplate;

	// 10초마다 비동기 방식으로 Consumer에 메시지를 전달
	// fixedRate는 메서드 실행 간격을 밀리초 단위로 지정
	// initialDelay는 초기 실행 지연 시간을 밀리초 단위로 지정
	//@Scheduled(fixedRate = 1000*10, initialDelay = 1000*5)
	public void produceMessage(Long eventId, User user) {
		log.info("Produce Message - BEGIN");
		String message = String.format("%d 번째 메세지를 %s 에 전송 하였습니다.", runningId++, LocalDateTime.now());

		// 이벤트 메시지 생성
		String eventMessage = String.format("{\"eventId\": %d, \"userId\": \"%s\", \"applicationTime\": \"%s\"}", eventId, user.getId(), LocalDateTime.now());

		// 선택할 파티션 계산
		// ex) eventId 1 -> partiction 0, eventId 2 -> partiction 1
		int partition = (eventId.intValue() - 1) % 10;

		// ListenableFuture는 비동기 작업의 결과를 나타내는 인터페이스이고 비동기 작업의 상태 추적, 콜백 등록 가능
		// KafkaTemplate의 send() 메서드를 호출하여 비동기적으로 메시지를 전송
		// SendResult 객체에는 전송 결과에 대한 정보가 포함됩니다.
		ListenableFuture<SendResult<Long, String>> listenableFuture = kafkaTemplate.send("booker", partition,  eventId, eventMessage);


		// 비동기 전송의 성공 또는 실패에 대한 콜백을 등록
		// 성공적으로 전송된 경우 onSuccess() 메서드가 호출되고, 실패한 경우 onFailure() 메서드가 호출
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
			@Override
			public void onFailure(Throwable ex) {
				log.debug("ERROR Kafka error happened", ex);
			}

			@Override
			public void onSuccess(SendResult<Long, String> result) {
				log.debug("SUCCESS!! This is the result: {}", result);
			}
		});

		log.info("Produce Message - END {}", message);
	}
	public void produceMessage_Batch(BatchDto batchDto, User user) {
		log.info("Produce Message - BEGIN");
		String message = String.format("%d 번째 메세지를 %s 에 전송 하였습니다.", runningId++, LocalDateTime.now());
		Long eventId = batchDto.getEventId();
		batchDto.setUserId(user.getUserId());
		// 선택할 파티션 계산
		// ex) eventId 1 -> partiction 0, eventId 2 -> partiction 1
		int partition = (eventId.intValue() - 1) % 10;
		ListenableFuture<SendResult<Long, BatchDto>> listenableFuture = kafkaBatchTemplate.send("booker", partition, eventId, batchDto);

		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, BatchDto>>() {
			@Override
			public void onFailure(Throwable ex) {
				log.debug("ERROR Kafka error happened", ex);
			}

			@Override
			public void onSuccess(SendResult<Long, BatchDto> result) {
				log.debug("SUCCESS!! This is the result: {}", result);
			}
		});

		log.info("Produce Message - END {}", message);
	}
}