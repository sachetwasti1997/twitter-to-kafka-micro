package com.twitter.to.kafka.twitter_to_kafka.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.twitter.to.kafka.twitter_to_kafka.kafka.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.Status;

@Component
public class TwitterStatusToAvroTransformer {
    public String getModelFromStatus(Status status) {
         TwitterAvroModel twitterAvroModel = TwitterAvroModel
                 .builder()
                .id(String.valueOf(status.getId()))
                .userId(status.getUser().getId())
                .createdAt(status.getCreatedAt().getTime())
                .text(status.getText())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(twitterAvroModel);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
