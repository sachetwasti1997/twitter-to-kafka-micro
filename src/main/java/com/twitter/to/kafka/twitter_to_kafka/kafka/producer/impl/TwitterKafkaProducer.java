package com.twitter.to.kafka.twitter_to_kafka.kafka.producer.impl;

import com.google.gson.Gson;
import com.twitter.to.kafka.twitter_to_kafka.kafka.model.TwitterAvroModel;
import com.twitter.to.kafka.twitter_to_kafka.kafka.producer.KafkaProducer;
import jakarta.annotation.PreDestroy;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

@Service
public class TwitterKafkaProducer implements KafkaProducer<Long, String> {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaProducer.class);

    private final KafkaTemplate<Long, String> kafkaTemplate;

    public TwitterKafkaProducer(KafkaTemplate<Long, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, Long key, String value) {
//        LOGGER.info("Sending message: {}, to topic: {}", value, topicName);
        ProducerRecord<Long, String> producerRecord = new ProducerRecord<>(topicName, key, value);
        CompletableFuture<SendResult<Long, String>> future = kafkaTemplate.send(producerRecord);
        addCallBack(topicName, value, future);
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            LOGGER.info("Closing the kafka producer!");
            kafkaTemplate.destroy();
        }
    }

    private static void addCallBack(String topicName, String value,
                                    CompletableFuture<SendResult<Long, String>> future) {
        future.whenComplete((res, err) -> {
            if(err != null) {
                LOGGER.error("Failed to send message: {}, to topic: {}", value, topicName);
            }
            if (null != res) {
                RecordMetadata metadata = res.getRecordMetadata();
                LOGGER.info("Topic: {}, Partition: {}, Offset: {}, TimeStamp: {}, at time: {}",
                            metadata.topic(),
                            metadata.partition(),
                            metadata.offset(),
                            metadata.timestamp(),
                            System.currentTimeMillis()
                        );
            }
        });
    }
}
