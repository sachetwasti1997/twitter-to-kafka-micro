package com.twitter.to.kafka.twitter_to_kafka.listener;

import com.twitter.to.kafka.twitter_to_kafka.exception.TwitterException;

public interface StatusListener {
    void onStatus() throws TwitterException;
}
