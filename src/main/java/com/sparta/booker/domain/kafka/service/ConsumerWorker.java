//package com.sparta.booker.domain.kafka.service;
//
//import com.sparta.booker.domain.event.document.sendFailure;
//import com.sparta.booker.domain.event.entity.Event;
//import com.sparta.booker.domain.event.entity.EventRequest;
//import com.sparta.booker.domain.event.repository.EventRepository;
//import com.sparta.booker.domain.event.repository.EventRequestRepository;
//import com.sparta.booker.domain.event.repository.SendFailureRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.errors.WakeupException;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Map;
//
//
//@Slf4j
//public class ConsumerWorker implements Runnable {
//
//    private final Map<String, Object> configs;
//    private final String topic;
//    private final String threadName;
//    private final KafkaConsumer<Long, String> consumer;
//    private final EventRepository eventRepository;
//    private final EventRequestRepository eventRequestRepository;
//    private final SendFailureRepository sendFailureRepository;
//
//    public ConsumerWorker(Map<String, Object> configs, String topic, int number,
//                          EventRepository eventRepository, EventRequestRepository eventRequestRepository,
//                          SendFailureRepository sendFailureRepository) {
//        this.configs = configs;
//        this.topic = topic;
//        this.threadName = "consumer-thread-" + number;
//        this.consumer = new KafkaConsumer<>(configs);
//        this.eventRepository = eventRepository;
//        this.eventRequestRepository = eventRequestRepository;
//        this.sendFailureRepository = sendFailureRepository;
//    }
//
//    @Override
//    public void run() {
//        consumer.subscribe(Arrays.asList(topic));
//        try {
//            while (true) {
//                ConsumerRecords<Long, String> records = consumer.poll(Duration.ofSeconds(1));
//                for (ConsumerRecord<Long, String> record : records) {
//                    log.debug("{}", record);
//                    processMessage(record);
//                }
//                consumer.commitSync();
//            }
//        } catch (WakeupException e) {
//            System.out.println(threadName + " trigger WakeupException");
//        } finally {
//            consumer.commitSync();
//            consumer.close();
//        }
//    }
//
//    private void processMessage(ConsumerRecord<Long, String> record) {
//        log.debug("Received Message : {}", record.value());
//        long startTime = System.currentTimeMillis();
//
//        // Event 메시지 파싱
//        try {
//            JSONObject eventJson = new JSONObject(record.value());
//            Long eventId = eventJson.getLong("eventId");
//            String userId = eventJson.getString("userId");
//            String applicationDate = eventJson.getString("applicationDate");
//            String applicationTime = eventJson.getString("applicationTime");
//
//            Event event = eventRepository.findById(eventId).get();
//            int bookCnt = event.getBook_cnt();
//            boolean isSuccess = event.getBook_cnt() > 0;
//
//            // 이벤트 결과 메시지 전송
//            if (isSuccess) {
//                sendSuccessMessage(eventId, userId, applicationDate, applicationTime);
//                bookCnt--;
//
//                //book_cnt 업데이트
//                event.update(bookCnt);
//                if (bookCnt == 0) {
//                    event.setReason("책이 모두 소진되었습니다");
//                }
//            }
//
//            long endTime = System.currentTimeMillis();
//            log.info("Processing Time for Message: {} ms", endTime - startTime);
//
//        } catch (JSONException e) {
//            log.error("Error parsing event message: {}", e.getMessage());
//        }
//    }
//
//    public void sendSuccessMessage(Long eventId, String userId, String applicationDate, String applicationTime) {
//        log.debug("Event ID : {}, User ID : {}, Time : {} - 이벤트 신청 성공", eventId, userId, applicationDate, applicationTime);
//        eventRequestRepository.save(new EventRequest(eventId, userId, applicationDate, applicationTime));
//    }
//
//    public void shutdown() {
//        consumer.wakeup();
//    }
//}
