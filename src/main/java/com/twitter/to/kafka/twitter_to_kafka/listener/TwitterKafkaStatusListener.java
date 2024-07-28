package com.twitter.to.kafka.twitter_to_kafka.listener;

import com.twitter.to.kafka.twitter_to_kafka.config.KafkaConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterKafkaStatusListener extends StatusAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);

    private final KafkaConfigData configData;

    public TwitterKafkaStatusListener(KafkaConfigData configData) {
        this.configData = configData;
    }

    @Override
    public void onStatus(Status status) {
        LOGGER.info("Received Status Text: {}, sending to the topic: {}", status.getText(), configData.getTopicName());
    }
}
