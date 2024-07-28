package com.twitter.to.kafka.twitter_to_kafka.init.impl;

import com.twitter.to.kafka.twitter_to_kafka.config.KafkaConfigData;
import com.twitter.to.kafka.twitter_to_kafka.init.StreamInitializer;
import com.twitter.to.kafka.twitter_to_kafka.kafka.admin.KafkaAdminClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializer implements StreamInitializer {
    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaStreamInitializer.class);

    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    public KafkaStreamInitializer(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaAdminClient = kafkaAdminClient;
    }

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        LOGGER.info("Ready for Operation topic(s): {}", kafkaConfigData.getTopicName());
    }
}
