package com.sparta.booker.kafka.service;

import com.sparta.booker.domain.user.entity.User;
import com.sparta.booker.kafka.config.KafkaProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class KafkaConsumer {

	// 이벤트 성공한 신청자 수를 세는 변수
	private final AtomicInteger successCounter = new AtomicInteger(0);

	@KafkaListener(topics = "book", groupId = KafkaProperties.CONSUMER_GROUP_ID)
	public void consumeMessage(ConsumerRecord<Long, String> record) {
		log.info("Received Message : {}", record.value());

		// Event 메시지 파싱
		try {
			JSONObject eventJson = new JSONObject(record.value());
			Long eventId = eventJson.getLong("eventId");
			String userId = eventJson.getString("userId");
			String timestamp = eventJson.getString("timestamp");

			// 이벤트 성공, 실패 여부 판단
			boolean isSuccess = successCounter.getAndIncrement() < 3; // 성공 카운터가 10보다 작을 경우에만 성공으로 처리

			// 이벤트 결과 메시지 전송
			if (isSuccess) {
				sendSuccessMessage(eventId, userId);
			} else {
				sendFailureMessage(eventId, userId);
			}

			// 데이터베이스에 저장

//			log.info("Event ID: {}", eventId);
//			log.info("User ID: {}", userId);
//			log.info("Timestamp: {}", timestamp);
		} catch (JSONException e) {
			log.error("Error parsing event message: {}", e.getMessage());
		}
	}

	private void sendSuccessMessage(Long eventId, String userId) {
		log.info("Event ID {} for User ID {} - Event succeeded", eventId, userId);
		// 이벤트 성공 메시지를 전송하는 로직을 구현합니다.
	}

	private void sendFailureMessage(Long eventId, String userId) {
		log.info("Event ID {} for User ID {} - Event failed", eventId, userId);
		// 이벤트 실패 메시지를 전송하는 로직을 구현합니다.
	}

}