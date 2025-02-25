package com.project.challenge.infrastructure.persistence.repository;

import com.project.challenge.domain.entity.Spaceship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Integer>,
                                             JpaSpecificationExecutor<Spaceship>{


}
