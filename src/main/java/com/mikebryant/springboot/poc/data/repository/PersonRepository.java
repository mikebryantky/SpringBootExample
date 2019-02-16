package com.mikebryant.springboot.poc.data.repository;

import com.mikebryant.springboot.poc.data.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

}
