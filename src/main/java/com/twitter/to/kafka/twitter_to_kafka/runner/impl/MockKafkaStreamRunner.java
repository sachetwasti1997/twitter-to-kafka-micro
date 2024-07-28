package com.twitter.to.kafka.twitter_to_kafka.runner.impl;

import com.twitter.to.kafka.twitter_to_kafka.config.TwitterToKafkaConfigData;
import com.twitter.to.kafka.twitter_to_kafka.exception.TwitterException;
import com.twitter.to.kafka.twitter_to_kafka.listener.impl.TwitterKafkaStatusListener;
import com.twitter.to.kafka.twitter_to_kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockKafkaStreamRunner implements StreamRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockKafkaStreamRunner.class);

    private final TwitterToKafkaConfigData twitterToKafkaConfigData;
    private final TwitterKafkaStatusListener statusListener;

    public MockKafkaStreamRunner(TwitterToKafkaConfigData twitterToKafkaConfigData,
                                 TwitterKafkaStatusListener statusListener) {
        this.twitterToKafkaConfigData = twitterToKafkaConfigData;
        this.statusListener = statusListener;
    }

    @Override
    public void start() throws TwitterException {

    }
}
