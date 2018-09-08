package io.cloudtraining.springboot.jwt.integration.service;

import io.cloudtraining.springboot.jwt.integration.domain.RandomCity;
import io.cloudtraining.springboot.jwt.integration.domain.User;

import java.util.List;


public interface GenericService {
    User findByUsername(String username);

    List<User> findAllUsers();

    List<RandomCity> findAllRandomCities();
}
