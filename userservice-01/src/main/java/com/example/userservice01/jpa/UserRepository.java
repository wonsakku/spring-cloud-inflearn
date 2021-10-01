package com.example.userservice01.jpa;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long>{

	Optional<UserEntity> findByUserId(String userId);

	Optional<UserEntity> findByEmail(String email);

}
