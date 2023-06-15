package com.sparta.booker.kafka.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
//KafkaConfig -> Spring Kafka에서 사용하는 Kafka 구성 정보를 정의하는 클래스
//kafkaProperties에서 읽은 Kafka 구성 속성을 기반으로 Kafka 클러스터와의 연결을 설정
//프로듀서 및 컨슈머에 대한 공통 구성을 정의. 또한, 특정 컨슈머 그룹이나 토픽에 대한 추가적인 설정을 포함
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;

    // Producer config
    @Bean
    // KafkaTemplate는 producer를 wraps 하고 Topic에 데이터를 보내는 편리한 방법을 제공
    public KafkaTemplate<Long, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ProducerFactory<Long, String> producerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG, "1");

        return new DefaultKafkaProducerFactory<>(config);
    }

    // Consumer config
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long,String>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Long, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(5);
        return factory;
    }

    @Bean
    public ConsumerFactory<Long, String> consumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, KafkaProperties.CONSUMER_GROUP_ID);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }
}