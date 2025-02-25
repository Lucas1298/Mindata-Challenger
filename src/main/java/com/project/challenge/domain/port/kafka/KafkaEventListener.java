package com.project.challenge.domain.port.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaEventListener {

    @KafkaListener(topics = "create-spaceship-topic", groupId = "my-group")
    public void createSpashipConsume(String message) {
        log.info("created spaceship: " + message);
    }

    @KafkaListener(topics = "update-spaceship-topic", groupId = "my-group")
    public void updateSpashipConsume(String message) {
        log.info("updated spaceship: " + message);
    }


    @KafkaListener(topics = "delete-spaceship-topic", groupId = "my-group")
    public void deleteSpashipConsume(String message) {
        log.info("deleted spaceship: " + message);
    }

}
