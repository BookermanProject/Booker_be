//package com.sparta.booker.kafka.controller;
//
//import com.sparta.booker.kafka.service.KafkaProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class KafkaController {
//
//	@Autowired
//	private KafkaProducer producer;
//
//	@PostMapping(value = "/sendMessage")
//	public void sendMessage(String message) {
//		producer.sendMessage(message);
//	}
//}