package com.project.challenge.domain.port.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventPublisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic,String message) {
        kafkaTemplate.send(topic, message);
    }
}
