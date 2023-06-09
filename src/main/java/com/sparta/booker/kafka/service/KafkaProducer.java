package com.sparta.booker.kafka.service;

import com.sparta.booker.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import org.springframework.kafka.support.SendResult;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducer {

	private static int runningId = 0;

	// Kafka 메시지를 전송하기 위한 Spring Kafka의 KafkaTemplate 객체
	// 이 객체를 통해 메시지를 생성하고 전송할 수 있다
	private final KafkaTemplate<Long, String> kafkaTemplate;

	// 10초마다 비동기 방식으로 Consumer에 메시지를 전달
	// fixedRate는 메서드 실행 간격을 밀리초 단위로 지정
	// initialDelay는 초기 실행 지연 시간을 밀리초 단위로 지정
	//@Scheduled(fixedRate = 1000*10, initialDelay = 1000*5)
	public void produceMessage(Long eventId, User user) {
		log.info("Produce Message - BEGIN");
		String message = String.format("%d 번째 메세지를 %s 에 전송 하였습니다.", runningId++, LocalDateTime.now());

		// 이벤트 메시지 생성
		String eventMessage = String.format("{\"eventId\": %d, \"userId\": \"%s\", \"timestamp\": \"%s\"}", eventId, user.getId(), LocalDateTime.now());

		// ListenableFuture는 비동기 작업의 결과를 나타내는 인터페이스이고 비동기 작업의 상태 추적, 콜백 등록 가능
		// KafkaTemplate의 send() 메서드를 호출하여 비동기적으로 메시지를 전송
		// SendResult 객체에는 전송 결과에 대한 정보가 포함됩니다.
		ListenableFuture<SendResult<Long, String>> listenableFuture = kafkaTemplate.send("book", 0, eventId, eventMessage);

		// 비동기 전송의 성공 또는 실패에 대한 콜백을 등록
		// 성공적으로 전송된 경우 onSuccess() 메서드가 호출되고, 실패한 경우 onFailure() 메서드가 호출
		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<Long, String>>() {
			@Override
			public void onFailure(Throwable ex) {
				log.error("ERROR Kafka error happened", ex);
			}

			@Override
			public void onSuccess(SendResult<Long, String> result) {
				log.info("SUCCESS!! This is the result: {}", result);
			}
		});

		log.info("Produce Message - END {}", message);
	}
}

