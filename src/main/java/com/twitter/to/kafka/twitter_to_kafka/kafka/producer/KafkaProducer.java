package com.twitter.to.kafka.twitter_to_kafka.kafka.producer;

import java.io.Serializable;

public interface KafkaProducer<K extends Serializable, V extends Serializable> {
    void send(String topicName, K key, V value);
}
