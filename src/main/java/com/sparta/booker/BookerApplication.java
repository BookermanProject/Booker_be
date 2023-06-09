package com.sparta.booker;

import com.sparta.booker.domain.kafka.config.KafkaProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(value={KafkaProperties.class})
@EnableScheduling
@EnableCaching
public class BookerApplication {
	public static void main(String[] args) {
		SpringApplication.run(BookerApplication.class, args);
	}

}
