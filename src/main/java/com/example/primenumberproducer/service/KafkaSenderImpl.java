package com.example.primenumberproducer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class KafkaSenderImpl implements KafkaSender {

    @Value("${spring.kafka.consumer.topic}")
    private String topicName;

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void send(String message) {
        CompletableFuture<SendResult<String, String>> future = this.kafkaTemplate
                .send(this.topicName, UUID.randomUUID().toString(), message);
        future.whenComplete((result, exception) -> {
            if (exception == null) {
                log.info("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                log.error("Unable to send message=[" +
                        message + "] due to : " + exception.getMessage());
            }
        });
    }
}
