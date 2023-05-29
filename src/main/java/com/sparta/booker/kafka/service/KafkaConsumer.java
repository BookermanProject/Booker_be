package com.sparta.booker.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaConsumer {

	@KafkaListener(topics = "book", groupId = "booker")
	public void consume(String message) throws IOException {
		System.out.println("receive message :" + message);
	}
}