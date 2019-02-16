package com.mikebryant.springboot.poc.data.service;

import com.mikebryant.springboot.poc.data.model.Person;
import com.mikebryant.springboot.poc.data.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private PersonRepository repository;


    public Person save(Person person) {
        return repository.save(person);
    }

    public Person get(String uuid) {
        Optional<Person> optionalPerson = repository.findById(uuid);
        return optionalPerson.orElseThrow(() -> new EntityNotFoundException("No person found with uuid " + uuid));
    }

    public void delete(String uuid) {
        repository.deleteById(uuid);
    }

    public List<Person> getAll() {
        return repository.findAll();
    }


}
