package com.mikebryant.springboot.poc.data.repository;

import com.mikebryant.springboot.poc.data.model.Department;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends CrudRepository<Department, String> {

}
