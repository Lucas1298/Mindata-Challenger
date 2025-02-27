package com.project.challenge.infrastructure.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@Slf4j
public class LogNegativeIdAspect {

    @Before("execution(* com.project.challenge.domain.port.spaceship.SpaceshipService.findSpaceship(..)) && args(id)")
    public void logNegativeId(JoinPoint joinPoint, Integer id) {
        if (Optional.ofNullable(id).isPresent() && id < 0) {
            log.warn("Se intentó buscar una nave con un ID negativo: {}", id);
        }
    }


}
