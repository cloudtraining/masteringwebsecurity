package io.cloudtraining.springboot.jwt.integration.repository;

import io.cloudtraining.springboot.jwt.integration.domain.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
