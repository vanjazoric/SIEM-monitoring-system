package com.center.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.User;

public interface UserRepository extends MongoRepository<User,String>{

	User findByUsername(String username);
}
