package io.cloudtraining.springboot.jwt.integration.repository;

import io.cloudtraining.springboot.jwt.integration.domain.RandomCity;
import org.springframework.data.repository.CrudRepository;


public interface RandomCityRepository extends CrudRepository<RandomCity, Long> {
}
