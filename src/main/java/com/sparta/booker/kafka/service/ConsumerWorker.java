//package com.sparta.booker.kafka.service;
//
//import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.clients.consumer.ConsumerRecord;
//import org.apache.kafka.clients.consumer.ConsumerRecords;
//import org.apache.kafka.clients.consumer.KafkaConsumer;
//import org.apache.kafka.common.errors.WakeupException;
//
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.Properties;
//
//@Slf4j
//public class ConsumerWorker implements Runnable {
//
//    private final Properties prop;
//    private final String topic;
//    private final String threadName;
//    private KafkaConsumer<String, String> consumer;
//
//    ConsumerWorker(Properties prop, String topic, int number) {
//        this.prop = prop;
//        this.topic = topic;
//        this.threadName = "consumer-thread-" + number;
//    }
//
//    @Override
//    public void run() {
//        consumer = new KafkaConsumer<>(prop);
//        consumer.subscribe(Arrays.asList(topic));
//        try {
//            while (true) {
//                ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(1));
//                for (ConsumerRecord<String, String> record : records) {
//                    log.info("{}", record);
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
//    public void shutdown() {
//        consumer.wakeup();
//    }
//}
