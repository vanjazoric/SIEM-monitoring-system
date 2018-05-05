package com.center.repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.Center;

public interface CenterRepository extends MongoRepository<Center, String> {

}