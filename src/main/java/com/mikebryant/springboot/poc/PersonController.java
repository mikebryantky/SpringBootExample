package com.mikebryant.springboot.poc;

import com.mikebryant.springboot.poc.data.model.Person;
import com.mikebryant.springboot.poc.data.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
public class PersonController {
    @Autowired
    private PersonService personService;


    @CrossOrigin
    @RequestMapping(
            value = "/ping",
            method = RequestMethod.GET,
            produces = "plain/text")
    public ResponseEntity<String> ping() {
        log.debug("Ping");
        String response = "pong";

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/person",
            method = RequestMethod.POST,
            produces = "application/json")
    public ResponseEntity<Person> add(@Valid @RequestBody Person person) {
        log.debug("Add person: " + person.toString());
        person = personService.save(person);

        return new ResponseEntity<>(person, HttpStatus.CREATED);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/person",
            method = RequestMethod.PUT,
            produces = "application/json")
    public ResponseEntity<Person> update(@Valid @RequestBody Person person) {
        log.debug("Add person: " + person.toString());
        person = personService.save(person);

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/person/{uuid}",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<Person> get(@PathVariable String uuid) {
        log.debug("Get person: " + uuid);
        Person person = personService.get(uuid);

        return new ResponseEntity<>(person, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/person",
            method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity<List<Person>> getAll() {
        log.debug("Get all person");
        List<Person> persons = personService.getAll();

        return new ResponseEntity<>(persons, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(
            value = "/person/{uuid}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    public ResponseEntity<String> delete(@PathVariable String uuid) {
        log.debug("Delete person: " + uuid);
        personService.delete(uuid);

        return new ResponseEntity<>(" { \"status\": \"OK\" } ", HttpStatus.OK);
    }
}
