package com.sparta.booker.kafka.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka")
@Data
//Kafka 프로듀서 및 컨슈머의 구성 속성을 저장하는 데 사용
//부트스트랩 서버, 직렬화 클래스, 토픽 구성 등의 설정을 포함할 수 있다
public class KafkaProperties {
    public static final String CONSUMER_GROUP_ID = "mysql-group";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;
}
