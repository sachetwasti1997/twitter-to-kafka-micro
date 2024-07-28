package com.twitter.to.kafka.twitter_to_kafka.exception;

public class TwitterException extends RuntimeException{
    public TwitterException() {
    }

    public TwitterException(String message) {
        super(message);
    }

    public TwitterException(String message, Throwable cause) {
        super(message, cause);
    }
}
