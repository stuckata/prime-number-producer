package com.example.primenumberproducer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.consumer.topic}")
    private String topicName;

    @Bean
    public NewTopic topic1() {
        return TopicBuilder.name(this.topicName)
                .partitions(1)
                .replicas(1)
                .compact()
                .build();
    }
}
