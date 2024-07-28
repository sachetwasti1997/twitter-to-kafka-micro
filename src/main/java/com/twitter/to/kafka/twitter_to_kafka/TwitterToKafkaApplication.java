package com.twitter.to.kafka.twitter_to_kafka;

import com.twitter.to.kafka.twitter_to_kafka.init.StreamInitializer;
import com.twitter.to.kafka.twitter_to_kafka.kafka.admin.KafkaAdminClient;
import com.twitter.to.kafka.twitter_to_kafka.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TwitterToKafkaApplication implements CommandLineRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(TwitterToKafkaApplication.class);

	private final StreamRunner streamRunner;
	private final StreamInitializer streamInitializer;

	public TwitterToKafkaApplication(StreamRunner streamRunner,
                                     StreamInitializer streamInitializer) {
        this.streamRunner = streamRunner;
        this.streamInitializer = streamInitializer;
    }

	public static void main(String[] args) {
		SpringApplication.run(TwitterToKafkaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		LOGGER.info("App Starts");
		streamInitializer.init();
		streamRunner.start();
	}
}
