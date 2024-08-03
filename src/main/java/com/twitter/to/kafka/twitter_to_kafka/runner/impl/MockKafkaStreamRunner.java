package com.twitter.to.kafka.twitter_to_kafka.runner.impl;

import com.twitter.to.kafka.twitter_to_kafka.config.KafkaConfigData;
import com.twitter.to.kafka.twitter_to_kafka.config.TwitterToKafkaServiceConfigData;
import com.twitter.to.kafka.twitter_to_kafka.exception.KafkaClientException;
import com.twitter.to.kafka.twitter_to_kafka.exception.TwitterException;
import com.twitter.to.kafka.twitter_to_kafka.listener.TwitterKafkaStatusListener;
import com.twitter.to.kafka.twitter_to_kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MockKafkaStreamRunner implements StreamRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(MockKafkaStreamRunner.class);

    private final TwitterToKafkaServiceConfigData kafkaServiceConfigData;
    private final TwitterKafkaStatusListener statusListener;

    public MockKafkaStreamRunner(TwitterToKafkaServiceConfigData kafkaServiceConfigData,
                                 TwitterKafkaStatusListener statusListener){
        this.kafkaServiceConfigData = kafkaServiceConfigData;
        this.statusListener = statusListener;
    }

    private static final Random RANDOM = new Random();
    private static final String[] TWEET_MESSAGE_LIST = new String[]{
            "Lorem ipsum",
            "ut perspiciatis",
            "error sit",
            "doloremque laudantium",
            "eaque ipsa quae",
            "et quasi architecto",
            "explicabo. Nemo",
            "voluptas sit",
            "sed quia",
            "ratione voluptatem",
            "quisquam est",
            "consectetur, adipisci",
            "modi tempora",
            "aliquam quaerat",
            "quis nostrum",
            "laboriosam consequatur?",
            "Quis autem",
            "voluptate velit",
            "vel illum",
            "Lorem ipsum",
            "adipiscing elit",
            "incididunt ut",
            "Ut enim",
            "exercitation ullamc",
            "aliquip ex",
            "Duis aute",
            "in voluptate",
            "eu fugiat",
            "sint occaecat",
            "sunt in",
            "deserunt moll."
    };

    private static final String TWEET_AS_RAW_JSON = "{"+
            "\"created_at\":\"{0}\", "+
            "\"id\":\"{1}\", "+
            "\"text\": \"{2}\", "+
            "\"user\": {\"id\":\"{3}\"} "+
            "}";

    private static final String TWITTER_STATUS_DATE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";

    @Override
    public void start() throws TwitterException, IOException, URISyntaxException {
        String [] keywords = kafkaServiceConfigData.getTwitterKeywords().toArray(new String[0]);
        int minTweetLength = kafkaServiceConfigData.getMockMinTweetLength();
        int maxTweetLength = kafkaServiceConfigData.getMockMaxTweetLength();
        long sleepTimeMs = kafkaServiceConfigData.getMockSleepMs();
        simulateTwitterStream(keywords, minTweetLength, maxTweetLength, sleepTimeMs);
    }

    private void simulateTwitterStream(String[] keywords, int minTweetLength, int maxTweetLength
            , long sleepMs) throws TwitterException, IOException, URISyntaxException {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while (true) {
                    String formattedRawJson = getFormattedRawJson(keywords, minTweetLength, maxTweetLength);
                    Status status = TwitterObjectFactory.createStatus(formattedRawJson);
                    LOGGER.info("Message {}", formattedRawJson);
                    statusListener.onStatus(status);
                    sleep(sleepMs);
                }
            }catch (TwitterException | twitter4j.TwitterException ex) {
                LOGGER.error("Error Creating twitter status");
            }
        });
    }

    private void sleep(long sleepTimeMs) {
        try {
            Thread.sleep(sleepTimeMs);
        } catch (InterruptedException e) {
            throw new KafkaClientException("Error on sleep while creating new status");
        }
    }

    private String getFormattedRawJson(String[] keywords, int minTweetLength, int maxTweetLength) {
        String[] params = new String[]{
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern(TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
                getRandomisedTweetContent(keywords, minTweetLength, maxTweetLength),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
        };
        String tweet = TWEET_AS_RAW_JSON;
        for (int i=0; i< params.length; i++) {
            tweet = tweet.replace("{"+i+"}", params[i]);
        }
        return tweet;
    }

    private String getRandomisedTweetContent(String[] keywords, int minTweetLength, int maxTweetLength) {
        StringBuilder tweetText = new StringBuilder();
        int tweetLength = (maxTweetLength - minTweetLength + 1) + minTweetLength;
        for (int i=0; i<tweetLength; i++) {
            tweetText.append(TWEET_MESSAGE_LIST[RANDOM.nextInt(TWEET_MESSAGE_LIST.length)]).append(" ");
            if (i == tweetLength / 2) {
                tweetText.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
            }
        }
        return tweetText.toString().trim();
    }
}
