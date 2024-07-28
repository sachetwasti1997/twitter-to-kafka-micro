package com.twitter.to.kafka.twitter_to_kafka.listener.impl;

import com.twitter.to.kafka.twitter_to_kafka.exception.TwitterException;
import com.twitter.to.kafka.twitter_to_kafka.listener.StatusListener;
import org.springframework.stereotype.Component;

@Component
public class TwitterKafkaStatusListener implements StatusListener {

    @Override
    public void onStatus() throws TwitterException {

    }
}
