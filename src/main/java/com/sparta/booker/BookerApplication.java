package com.sparta.booker;

import com.sparta.booker.kafka.config.KafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(value={KafkaProperties.class})
@EnableScheduling
public class BookerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookerApplication.class, args);
	}

}
