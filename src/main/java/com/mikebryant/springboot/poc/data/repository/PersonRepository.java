package com.mikebryant.springboot.poc.data.repository;

import com.mikebryant.springboot.poc.data.model.Person;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends PagingAndSortingRepository<Person, String> {

}
