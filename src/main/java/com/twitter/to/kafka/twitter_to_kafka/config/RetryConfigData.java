package com.twitter.to.kafka.twitter_to_kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * initial-interval-ms: 1000
 *   max-interval-ms: 10000
 *   multiplier: 2.0
 *   maxAttempts: 3
 *   sleep-time-ms: 2000
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "retry-config")
public class RetryConfigData {
    private Long maxIntervalMs;
    private Double multiplier;
    private Short maxAttempts;
    private Long initialIntervalMs;
    private Long sleepTimeMs;
}
