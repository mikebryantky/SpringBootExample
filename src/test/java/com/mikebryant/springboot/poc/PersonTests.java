package com.mikebryant.springboot.poc;

import com.mikebryant.springboot.poc.config.JsonMarshaller;
import com.mikebryant.springboot.poc.data.model.Department;
import com.mikebryant.springboot.poc.data.model.Person;
import com.mikebryant.springboot.poc.data.service.DepartmentService;
import com.mikebryant.springboot.poc.data.service.PersonService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PocApplication.class)
@WebAppConfiguration
@Transactional
public class PersonTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private PersonService personService;

    @Autowired
    private DepartmentService departmentService;
    
    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void addPerson() throws Exception {
        Department department = new Department();
        department.setName("New Person Department");
        departmentService.save(department);

        Person person = new Person();
        person.setFirstName("New First Name");
        person.setLastName("New Last Name");
        person.setBirthDate(LocalDate.of(1970, 11, 8));
        person.setEmailAddress("someone@somewhere.com");
        person.setDepartment(department);

        mockMvc.perform(post("/person")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").isNotEmpty())
                .andExpect(jsonPath("$.firstName").value(person.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(person.getLastName()))
                .andExpect(jsonPath("$.birthDate", is("1970-11-08")))
                .andExpect(jsonPath("$.emailAddress").value(person.getEmailAddress()))
                .andExpect(jsonPath("$.department.name").value(department.getName()))
                .andExpect(jsonPath("$.department.uuid").value(department.getUuid()))
                .andDo(print());
    }


    @Test
    public void updatePerson() throws Exception {
        String firstName = "Update First Name";

        Department department = new Department();
        department.setName("Update Person Department");
        departmentService.save(department);

        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName("Update Last Name");
        person.setBirthDate(LocalDate.of(1970, 11, 8));
        person.setEmailAddress("someone@somewhere.com");
        person.setDepartment(department);
        person = personService.save(person);

        assertNotNull(person.getUuid());
        String originalUuid = person.getUuid();

        Assert.assertEquals(person.getFirstName(), firstName);

        String newName = "Changed Update First name";
        person.setFirstName(newName);

        mockMvc.perform(put("/person")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(person))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(originalUuid))
                .andExpect(jsonPath("$.firstName").value(newName))
                .andDo(print())
                .andReturn();
    }

    @Test(expected = EntityNotFoundException.class)
    public void deletePerson() throws Exception {
        String name = "Delete Test Person";

        Department department = new Department();
        department.setName("Delete Person Department");
        departmentService.save(department);

        Person person = new Person();
        person.setFirstName(name);
        person.setLastName("Delete Last Name");
        person.setBirthDate(LocalDate.of(1970, 11, 8));
        person.setEmailAddress("someone@somewhere.com");
        person.setDepartment(department);
        person = personService.save(person);

        Assert.assertNotNull(person.getUuid());
        Assert.assertNotNull(personService.get(person.getUuid()));

        mockMvc.perform(delete("/person/" + person.getUuid()))
                .andExpect(status().isOk())
                .andDo(print());

        personService.get(person.getUuid());
    }

    public void errorOnNonExistentPerson() throws Exception {
        mockMvc.perform(delete("/person/" + UUID.randomUUID().toString()))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void listPersons() throws Exception {
        String name = "List Test Person";

        Department department = new Department();
        department.setName("List Person Department");
        departmentService.save(department);

        int numberPersons = 10;
        for(int i=1; i<=numberPersons; i++) {
            Person person = new Person();
            person.setFirstName(i + " " + name);
            person.setLastName("List Person Last Name");
            person.setBirthDate(LocalDate.of(1970, 11, 8));
            person.setEmailAddress("someone@somewhere.com");
            person.setDepartment(department);
            personService.save(person);
        }

        mockMvc.perform(get("/person")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(numberPersons))
                .andDo(print())
                .andReturn();
    }

}
