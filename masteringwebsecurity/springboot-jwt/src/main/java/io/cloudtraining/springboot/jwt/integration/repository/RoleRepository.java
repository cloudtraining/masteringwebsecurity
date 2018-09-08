package io.cloudtraining.springboot.jwt.integration.repository;

import io.cloudtraining.springboot.jwt.integration.domain.Role;
import org.springframework.data.repository.CrudRepository;


public interface RoleRepository extends CrudRepository<Role, Long> {
}
