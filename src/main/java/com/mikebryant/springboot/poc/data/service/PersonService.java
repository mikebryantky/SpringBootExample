package com.mikebryant.springboot.poc.data.service;

import com.mikebryant.springboot.poc.data.model.Person;
import com.mikebryant.springboot.poc.data.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PersonService {
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
