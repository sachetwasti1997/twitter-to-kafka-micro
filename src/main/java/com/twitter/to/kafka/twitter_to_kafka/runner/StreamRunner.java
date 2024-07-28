package com.twitter.to.kafka.twitter_to_kafka.runner;

import com.twitter.to.kafka.twitter_to_kafka.exception.TwitterException;

import java.io.IOException;
import java.net.URISyntaxException;

public interface StreamRunner {
    void start() throws TwitterException, IOException, URISyntaxException;
}
