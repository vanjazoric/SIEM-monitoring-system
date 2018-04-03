package com.center.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.center.domain.Center;

public interface CenterRepository extends JpaRepository<Center, Long> {

}