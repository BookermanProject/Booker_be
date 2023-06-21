package com.sparta.booker.domain.kafka.config;

import com.sparta.booker.domain.event.dto.BatchDto;
import com.sparta.booker.domain.event.repository.EventRepository;
import com.sparta.booker.domain.event.repository.EventRequestRepository;
import com.sparta.booker.domain.event.repository.SendFailureRepository;
import nonapi.io.github.classgraph.json.JSONSerializer;
//import com.sparta.booker.domain.kafka.service.ConsumerWorker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.KafkaFuture;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableKafka
@RequiredArgsConstructor
@Slf4j
//KafkaConfig -> Spring Kafka에서 사용하는 Kafka 구성 정보를 정의하는 클래스
//kafkaProperties에서 읽은 Kafka 구성 속성을 기반으로 Kafka 클러스터와의 연결을 설정
//프로듀서 및 컨슈머에 대한 공통 구성을 정의. 또한, 특정 컨슈머 그룹이나 토픽에 대한 추가적인 설정을 포함
public class KafkaConfig {

    private final KafkaProperties kafkaProperties;
    //private final static List<ConsumerWorker> workerThreads = new ArrayList<>();
    private final EventRepository eventRepository;
    private final EventRequestRepository eventRequestRepository;
    private final SendFailureRepository sendFailureRepository;

    // Producer config
    @Bean
    // KafkaTemplate는 producer를 wraps 하고 Topic에 데이터를 보내는 편리한 방법을 제공
    public KafkaTemplate<Long, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<Long, BatchDto> kafkaBatchTemplate() {
        return new KafkaTemplate<>(batchProducerFactory());
    }

    @Bean
    public Map<String, Object> ProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG, "1");
//        config.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(500*1024)); //700kb * 100 (75000*1024)
//        config.put(ProducerConfig.LINGER_MS_CONFIG, "5000"); //대기시간을 주는 값 ms단위 0.1초
        return config;
    }

    @Bean
    public ProducerFactory<Long, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(ProducerConfig());
    }


    //멀티스레드 방식 프로듀서 설정값
    @Bean
    public Map<String, Object> batchProducerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        config.put(org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG, "1");
//        config.put(ProducerConfig.BATCH_SIZE_CONFIG, Integer.toString(1000000)); //700kb * 100 (75000*1024)
//        config.put(ProducerConfig.LINGER_MS_CONFIG, "100"); //대기시간을 주는 값 ms단위 0.1초
        return config;
    }

    @Bean
    public ProducerFactory<Long, BatchDto> batchProducerFactory() {
        return new DefaultKafkaProducerFactory<>(batchProducerConfig());
    }



//    @Bean
//    public ProducerFactory<Long, String> producerFactory() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(org.apache.kafka.clients.producer.ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
//        config.put(org.apache.kafka.clients.producer.ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
//        config.put(org.apache.kafka.clients.producer.ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
//        config.put(org.apache.kafka.clients.producer.ProducerConfig.ACKS_CONFIG, "1");
//
//        return new DefaultKafkaProducerFactory<>(config);
//    }



    // Consumer config
    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<Long,String>> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<Long, String> factory
                = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        //factory.setConcurrency(5);
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

//    @Bean
//    public ConsumerFactory<Long, String> consumerFactory() {
//        Runtime.getRuntime().addShutdownHook(new KafkaConfig.ShutdownThread());
//        Map<String, Object> config = new HashMap<>();
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, KafkaProperties.CONSUMER_GROUP_ID);
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
////        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 30000);
////        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
//
//        int CONSUMER_COUNT = getPartitionSize(KafkaProperties.TOPIC_NAME);
//        log.info("=============== Set thread count: {} ===============", CONSUMER_COUNT);
//
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        for (int i = 0; i < CONSUMER_COUNT; i++) {
//            ConsumerWorker worker = new ConsumerWorker(config, KafkaProperties.TOPIC_NAME, i,
//                    eventRepository, eventRequestRepository, sendFailureRepository);
//            workerThreads.add(worker);
//            workerThreads.add(worker);
//            executorService.execute(worker);
//        }
//
//        return new DefaultKafkaConsumerFactory<>(config);
//    }
//
//
//    //주어진 토픽의 파티션 수를 얻는 메서드
//    public int getPartitionSize(String topic) {
//        log.info("=============== Get {} partition size ===============", topic);
//        int partitions;
//        Properties adminConfigs = new Properties();
//        adminConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
//        // AdminClient는 관리 작업을 수행하기 위해 Kafka 클러스터와 통신하고, 토픽, 파티션, 컨슈머 그룹 등과 관련된 메타데이터를 조회하고 수정할 수 있다.
//        AdminClient admin = AdminClient.create(adminConfigs);
//        try {
//            DescribeTopicsResult result = admin.describeTopics(Arrays.asList(topic));
//            Map<String, KafkaFuture<TopicDescription>> values = result.values();
//            KafkaFuture<TopicDescription> topicDescription = values.get(topic);
//            partitions = topicDescription.get().partitions().size();
//        } catch (Exception e) {
//            log.error(e.getMessage(), e);
//            partitions = 10;
//        }
//        admin.close();
//        return partitions;
//    }
//
//    // Kafka 클러스터의 기본 파티션 수를 조회하는 메서드
////    public int getDefaultPartitionSize() {
////        log.info("=============== getDefaultPartitionSize ===============");
////        int partitions = 1;
////        Properties adminConfigs = new Properties();
////        adminConfigs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
////        // AdminClient는 관리 작업을 수행하기 위해 Kafka 클러스터와 통신하고, 토픽, 파티션, 컨슈머 그룹 등과 관련된 메타데이터를 조회하고 수정할 수 있다.
////        AdminClient admin = AdminClient.create(adminConfigs);
////        try {
////            DescribeTopicsResult result = admin.describeTopics(Arrays.asList(topic));
////            Map<String, KafkaFuture<TopicDescription>> values = result.values();
////            KafkaFuture<TopicDescription> topicDescription = values.get(topic);
////            partitions = topicDescription.get().partitions().size();
////        } catch (Exception e) {
////            log.error(e.getMessage(), e);
////            partitions = 10;
////        }
////        admin.close();
////        return partitions;
////    }
//
//
//    static class ShutdownThread extends Thread {
//        public void run() {
//            workerThreads.forEach(ConsumerWorker::shutdown);
//            System.out.println("================== END ==================");
//        }
//    }


    //멀티스레드 방식 컨슈머 설정값
//    @Bean
//    public Map<String, Object> batchConsumerConfig() {
//        Map<String, Object> config = new HashMap<>();
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaProperties.getBootStrapServers());
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_ID_CONFIG, KafkaProperties.CONSUMER_GROUP_ID);
////        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
////        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JSONSerializer.class);
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//        config.put(org.apache.kafka.clients.consumer.ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1000");
//        return config;
//    }
//
//    @Bean
//    public ConsumerFactory<String, BatchDto> batchConsumerFactory() {
//        return new DefaultKafkaConsumerFactory<>(batchConsumerConfig(), new StringDeserializer(),
//                new JsonDeserializer<>(BatchDto.class, false));
//    }
//
//    @Bean("batchKafkaListenerContainerFactory")
//    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, BatchDto>> batchkafkaListenerContainerFactory(){
//        ConcurrentKafkaListenerContainerFactory<String, BatchDto> factory
//                = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(batchConsumerFactory());
//        factory.setBatchListener(true);
//        return factory;
//    }
}