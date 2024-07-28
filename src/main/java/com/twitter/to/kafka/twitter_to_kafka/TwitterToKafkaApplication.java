package com.twitter.to.kafka.twitter_to_kafka;

import com.twitter.to.kafka.twitter_to_kafka.config.KafkaConfigData;
import com.twitter.to.kafka.twitter_to_kafka.runner.impl.MockKafkaStreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterToKafkaApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaApplication.class);

	private final KafkaConfigData kafkaConfigData;
	private final MockKafkaStreamRunner mockKafkaStreamRunner;

	public TwitterToKafkaApplication(KafkaConfigData configData,
									 MockKafkaStreamRunner mockKafkaStreamRunner) {
		this.kafkaConfigData = configData;
        this.mockKafkaStreamRunner = mockKafkaStreamRunner;
    }

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("Logging from config-server: {}", this.kafkaConfigData);
		mockKafkaStreamRunner.start();
	}
}
