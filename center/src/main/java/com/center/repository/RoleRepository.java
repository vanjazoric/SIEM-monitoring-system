package com.center.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.Role;

public interface RoleRepository extends MongoRepository<Role,String>{
	
	Role findRoleByName(String role);

}
