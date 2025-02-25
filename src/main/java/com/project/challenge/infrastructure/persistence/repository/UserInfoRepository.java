package com.project.challenge.infrastructure.persistence.repository;

import com.project.challenge.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<Users, Integer> {
    Optional<Users> findByUsername(String name);
}