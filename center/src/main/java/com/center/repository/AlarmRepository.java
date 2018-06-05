package com.center.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.Alarm;

public interface AlarmRepository extends MongoRepository<Alarm, String> {

}
