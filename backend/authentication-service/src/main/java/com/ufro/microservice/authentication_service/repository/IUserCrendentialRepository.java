package com.ufro.microservice.authentication_service.repository;

import com.ufro.microservice.authentication_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;

import java.util.Optional;

public interface IUserCrendentialRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(String id);
    boolean existsByEmail (String email);
    @Query("{'email': ?0}")
    @Update("{'$set': {'password': ?1}}")
    long updateUserByEmail(String email, String password);
}
