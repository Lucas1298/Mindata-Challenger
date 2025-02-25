package com.project.challenge.infrastructure.persistence;

import com.project.challenge.domain.entity.Spaceship;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;

public class SpaceshipSpecification {

    public static Specification<Spaceship> filterByParams(Map<String, String> filters) {
        return (Root<Spaceship> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                String field = entry.getKey();
                String value = entry.getValue();

                if (root.get(field).getJavaType().equals(String.class)) {
                    predicate = cb.and(predicate, cb.like(cb.lower(root.get(field)), "%" + value.toLowerCase() + "%"));
                } else if (root.get(field).getJavaType().equals(Double.class)) {
                    predicate = cb.and(predicate, cb.equal(root.get(field), Double.valueOf(value)));
                } else if (root.get(field).getJavaType().equals(Integer.class)) {
                    predicate = cb.and(predicate, cb.equal(root.get(field), Integer.valueOf(value)));
                }
            }
            return predicate;
        };
    }
}