package com.ashley.testbuilder.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ashley.testbuilder.data.entity.Step;

public interface StepRepository extends JpaRepository<Step, Long>{

	@Query("select s from Step s JOIN s.test t where t.id = :searchTerm")
	List<Step> findStepsByTestId(@Param("searchTerm") String searchTerm);
}
