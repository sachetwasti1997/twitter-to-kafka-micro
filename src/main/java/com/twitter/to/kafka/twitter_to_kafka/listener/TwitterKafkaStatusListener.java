package com.twitter.to.kafka.twitter_to_kafka.listener;

import com.twitter.to.kafka.twitter_to_kafka.config.KafkaConfigData;
import com.twitter.to.kafka.twitter_to_kafka.kafka.model.TwitterAvroModel;
import com.twitter.to.kafka.twitter_to_kafka.kafka.producer.KafkaProducer;
import com.twitter.to.kafka.twitter_to_kafka.transformer.TwitterStatusToAvroTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
public class TwitterKafkaStatusListener extends StatusAdapter {
    private final static Logger LOGGER = LoggerFactory.getLogger(TwitterKafkaStatusListener.class);

    private final KafkaConfigData configData;
    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;
    private final TwitterStatusToAvroTransformer transformer;

    public TwitterKafkaStatusListener(KafkaConfigData configData, KafkaProducer<Long, TwitterAvroModel> kafkaProducer, TwitterStatusToAvroTransformer transformer) {
        this.configData = configData;
        this.kafkaProducer = kafkaProducer;
        this.transformer = transformer;
    }

    @Bean
    SmartLifecycle connector(ProducerFactory<Long ,TwitterAvroModel> pf) {
        return new SmartLifecycle() {

            @Override
            public void stop() {
            }

            @Override
            public void start() {
                pf.createProducer().close();
            }

            @Override
            public boolean isRunning() {
                return false;
            }

        };
    }

    @Override
    public void onStatus(Status status) {
//        LOGGER.info("Received Status Text: {}, sending to the topic: {}", status.getText(), configData.getTopicName());
        TwitterAvroModel twitterAvroModel = transformer.getModelFromStatus(status);
        LOGGER.info("Sending the following TwitterAvroModel: {}", twitterAvroModel);
        kafkaProducer.send(configData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
