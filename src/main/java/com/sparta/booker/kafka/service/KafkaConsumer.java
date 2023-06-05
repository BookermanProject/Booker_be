package com.sparta.booker.kafka.service;

import com.sparta.booker.domain.event.entity.Event;
import com.sparta.booker.domain.event.repository.EventRepository;
import com.sparta.booker.kafka.config.KafkaProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class KafkaConsumer {

	private final EventRepository eventRepository;

	// 0~4 partiction 처리
	@KafkaListener(topicPartitions = @TopicPartition(topic = "book", partitions = {"0", "1", "2", "3", "4"}), groupId = KafkaProperties.CONSUMER_GROUP_ID)
	public void processPartitionOne(ConsumerRecord<Long, String> record) {
		processMessage(record);
	}

	// 5~9 partiction 처리
	@KafkaListener(topicPartitions = @TopicPartition(topic = "book", partitions = {"5", "6", "7", "8", "9"}), groupId = KafkaProperties.CONSUMER_GROUP_ID)
	public void processPartitionTwo(ConsumerRecord<Long, String> record) {
		processMessage(record);
	}


	private void processMessage(ConsumerRecord<Long, String> record) {
		log.info("Received Message : {}", record.value());

		// Event 메시지 파싱
		try {
			JSONObject eventJson = new JSONObject(record.value());
			Long eventId = eventJson.getLong("eventId");
			String userId = eventJson.getString("userId");

			// 이벤트 성공, 실패 여부 판단
			// book_cnt가 0 보다 클 경우에만 성공으로 처리
			Event event = eventRepository.findById(eventId).orElseThrow(
					() -> new NullPointerException("해당 이벤트는 존재하지 않습니다")
			);
			int bookCnt = event.getBook_cnt();
			boolean isSuccess = event.getBook_cnt() > 0;

			// 이벤트 결과 메시지 전송
			if (isSuccess) {
				sendSuccessMessage(eventId, userId);
				bookCnt--;

				//book_cnt 업데이트
				event.update(bookCnt);
			} else {
				sendFailureMessage(eventId, userId);
			}

			// 데이터베이스에 저장

		} catch (JSONException e) {
			log.error("Error parsing event message: {}", e.getMessage());
		}
	}

	private void sendSuccessMessage(Long eventId, String userId) {
		log.info("Event ID : {} for User ID : {} - 이벤트 신청 성공", eventId, userId);
		// 이벤트 성공 메시지를 전송하는 로직을 구현합니다.
	}

	private void sendFailureMessage(Long eventId, String userId) {
		log.info("Event ID : {} for User ID : {} - 이벤트 신청 실패", eventId, userId);
		// 이벤트 실패 메시지를 전송하는 로직을 구현합니다.
	}



}