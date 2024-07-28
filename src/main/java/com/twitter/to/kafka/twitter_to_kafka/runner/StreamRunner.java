package com.twitter.to.kafka.twitter_to_kafka.runner;

import com.twitter.to.kafka.twitter_to_kafka.exception.TwitterException;

public interface StreamRunner {
    void start() throws TwitterException;
}
