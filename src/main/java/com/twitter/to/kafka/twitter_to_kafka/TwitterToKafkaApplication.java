package com.twitter.to.kafka.twitter_to_kafka;

import com.twitter.to.kafka.twitter_to_kafka.config.TwitterToKafkaConfigData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterToKafkaApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaApplication.class);

	private final TwitterToKafkaConfigData twitterToKafkaConfigData;

	public TwitterToKafkaApplication(TwitterToKafkaConfigData configData) {
		this.twitterToKafkaConfigData = configData;
	}

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Logging from config-server: {}", this.twitterToKafkaConfigData);
	}
}
