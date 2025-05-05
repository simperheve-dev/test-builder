package com.ashley.testbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashley.testbuilder.data.entity.TestResult;

public interface TestResultRepository extends JpaRepository<TestResult, Long>{
	
}
