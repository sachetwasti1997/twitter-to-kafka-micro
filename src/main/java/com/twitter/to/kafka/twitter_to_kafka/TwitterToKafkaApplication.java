package com.twitter.to.kafka.twitter_to_kafka;

import com.twitter.to.kafka.twitter_to_kafka.config.KafkaConfigData;
import com.twitter.to.kafka.twitter_to_kafka.kafka.admin.KafkaAdminClient;
import com.twitter.to.kafka.twitter_to_kafka.runner.impl.MockKafkaStreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterToKafkaApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaApplication.class);

	private final MockKafkaStreamRunner mockKafkaStreamRunner;
	private final KafkaAdminClient kafkaAdminClient;

	public TwitterToKafkaApplication(MockKafkaStreamRunner mockKafkaStreamRunner,
									 KafkaAdminClient kafkaAdminClient) {
        this.mockKafkaStreamRunner = mockKafkaStreamRunner;
        this.kafkaAdminClient = kafkaAdminClient;
    }

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("App Starts");
		kafkaAdminClient.createTopics();
		mockKafkaStreamRunner.start();
	}
}
