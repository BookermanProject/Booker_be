package com.sparta.booker.kafka.service;

import com.sparta.booker.domain.event.document.sendFailure;
import com.sparta.booker.domain.event.entity.Event;
import com.sparta.booker.domain.event.entity.EventRequest;
import com.sparta.booker.domain.event.repository.EventRepository;
import com.sparta.booker.domain.event.repository.EventRequestRepository;
import com.sparta.booker.domain.event.repository.SendFailureRepository;
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
@Transactional
@RequiredArgsConstructor
public class KafkaConsumer {

	private final EventRepository eventRepository;
	private final EventRequestRepository eventRequestRepository;
	private final SendFailureRepository sendFailureRepository;

	private void processMessage(ConsumerRecord<Long, String> record) {
		log.info("Received Message : {}", record.value());

		// Event 메시지 파싱
		try {
			JSONObject eventJson = new JSONObject(record.value());
			Long eventId = eventJson.getLong("eventId");
			String userId = eventJson.getString("userId");
			String applicationTime = eventJson.getString("applicationTime");

			// 이벤트 성공, 실패 여부 판단
			// book_cnt가 0 보다 클 경우에만 성공으로 처리
			Event event = eventRepository.findById(eventId).orElseThrow(
					() -> new IllegalArgumentException("해당 이벤트는 존재하지 않습니다")
			);

			int bookCnt = event.getBook_cnt();
			boolean isSuccess = event.getBook_cnt() > 0;

			// 이벤트 결과 메시지 전송
			if (isSuccess) {
				sendSuccessMessage(eventId, userId, applicationTime);
				bookCnt--;

				//book_cnt 업데이트
				event.update(bookCnt);
				if (bookCnt == 0) {
					event.setReason("책이 모두 소진되었습니다");
				}
			} else {
				sendFailureMessage(eventId, userId, applicationTime);
			}

		} catch (JSONException e) {
			log.error("Error parsing event message: {}", e.getMessage());
		}
	}

	// 0~4 partiction 처리
	@KafkaListener(topicPartitions = @TopicPartition(topic = "booker", partitions = {"0", "1", "2", "3", "4"}), groupId = KafkaProperties.CONSUMER_GROUP_ID)
	public void processPartitionOne(ConsumerRecord<Long, String> record) {
		processMessage(record);
	}

	// 5~9 partiction 처리
	@KafkaListener(topicPartitions = @TopicPartition(topic = "booker", partitions = {"5", "6", "7", "8", "9"}), groupId = KafkaProperties.CONSUMER_GROUP_ID)
	public void processPartitionTwo(ConsumerRecord<Long, String> record) {
		processMessage(record);
	}


	public void sendSuccessMessage(Long eventId, String userId, String applicationTime) {
		log.info("Event ID : {}, User ID : {}, Time : {} - 이벤트 신청 성공", eventId, userId, applicationTime);
		eventRequestRepository.save(new EventRequest(eventId, userId, applicationTime));
	}

	public void sendFailureMessage(Long eventId, String userId, String applicationTime) {
		log.info("Event ID : {}, User ID : {}, Time : {} - 이벤트 신청 실패", eventId, userId, applicationTime);
		// (실패한 경우) 해당 이벤트 메시지 elasticsearch에 저장하는 로직
		sendFailure sendFailure = new sendFailure(eventId, userId, applicationTime);
		sendFailureRepository.save(sendFailure);
	}

}