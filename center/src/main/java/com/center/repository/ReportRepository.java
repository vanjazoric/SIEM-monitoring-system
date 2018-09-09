package com.center.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.center.domain.Report;

public interface ReportRepository extends MongoRepository<Report, String> {

}
