package com.twitter.to.kafka.twitter_to_kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-producer-config")
public class KafkaProducerConfigData {

    public String keySerializerClass;
    public String valueSerializerClass;
    public String compressionType;
    public String acks;
    public Integer batchSize;
    public Integer batchSizeBoostFactor;
    public Integer lingerMs;
    public Integer requestTimeoutMs;
    public Integer retryCount;

}
