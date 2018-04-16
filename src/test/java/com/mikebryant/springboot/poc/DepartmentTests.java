package com.mikebryant.springboot.poc;

import com.mikebryant.springboot.poc.config.JsonMarshaller;
import com.mikebryant.springboot.poc.data.model.Department;
import com.mikebryant.springboot.poc.data.service.DepartmentService;
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

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = PocApplication.class)
@WebAppConfiguration
@Transactional
public class DepartmentTests {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private JsonMarshaller jsonMarshaller;

    @Autowired
    private DepartmentService departmentService;

    @Before
    public void setup() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    public void addDepartment() throws Exception {
        Department department = new Department();
        department.setName("New Test Department");

        mockMvc.perform(post("/department")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(department))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.uuid").isNotEmpty())
                .andExpect(jsonPath("$.name").value(department.getName()))
                .andDo(print())
                .andReturn();
    }


    @Test
    public void updateDepartment() throws Exception {
        String name = "Update Test Department";
        Department department = new Department();
        department.setName(name);
        department = departmentService.save(department);

        assertNotNull(department.getUuid());
        String originalUuid = department.getUuid();

        Assert.assertEquals(department.getName(), name);

        String newName = "Changed Update Test Department";
        department.setName(newName);

        mockMvc.perform(put("/department")
                .contentType("application/json;charset=UTF-8")
                .content(jsonMarshaller.marshal(department))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(originalUuid))
                .andExpect(jsonPath("$.name").value(newName))
                .andDo(print())
                .andReturn();
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteDepartment() throws Exception {
        String name = "Delete Test Department";
        Department department = new Department();
        department.setName(name);
        department = departmentService.save(department);

        mockMvc.perform(delete("/department/" + department.getUuid()))
                .andExpect(status().isOk())
                .andDo(print());

        departmentService.get(department.getUuid());
    }

    @Test
    public void listDepartments() throws Exception {
        String name = "List Test Department";

        int numberDepartments = 10;
        for(int i=1; i<=numberDepartments; i++) {
            Department department = new Department();
            department.setName(i + " " + name);
            departmentService.save(department);
        }

        mockMvc.perform(get("/department")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(numberDepartments))
                .andDo(print())
                .andReturn();
    }

}
