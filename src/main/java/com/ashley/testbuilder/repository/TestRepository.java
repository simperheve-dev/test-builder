package com.ashley.testbuilder.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashley.testbuilder.data.entity.Test;

public interface TestRepository extends JpaRepository<Test, Long>{

}
