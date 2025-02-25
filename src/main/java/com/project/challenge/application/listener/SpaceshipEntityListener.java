package com.project.challenge.application.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.challenge.domain.entity.Spaceship;
import com.project.challenge.domain.port.kafka.KafkaEventPublisher;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EntityListeners(SpaceshipEntityListener.class)
@Component
public class SpaceshipEntityListener {

    @Autowired
    private KafkaEventPublisher spaceshipKafkaProducer;

    @Autowired
    private ObjectMapper objectMapper;

    @PostPersist
    public void prePersist(Spaceship spaceship) {
        spaceshipKafkaProducer.sendMessage("create-spaceship-topic", spaceshipToString(spaceship));
    }

    @PostUpdate
    public void preUpdate(Spaceship spaceship) {
        spaceshipKafkaProducer.sendMessage("update-spaceship-topic", spaceshipToString(spaceship));
    }

    @PostRemove
    public void preRemove(Spaceship spaceship) {
        spaceshipKafkaProducer.sendMessage("delete-spaceship-topic", spaceshipToString(spaceship));
    }

    private String spaceshipToString(Spaceship spaceship) {
        try {
            return objectMapper.writeValueAsString(spaceship);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }



}
