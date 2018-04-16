package com.mikebryant.springboot.poc.data.service;

import com.mikebryant.springboot.poc.data.model.Department;
import com.mikebryant.springboot.poc.data.repository.DepartmentRepository;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private DepartmentRepository repository;

    public Department save(Department department) {
        Department newDepartment = repository.save(department);

        return newDepartment;
    }

    public Department get(String uuid) {
        Optional<Department> optionalDepartment = repository.findById(uuid);
        return optionalDepartment.orElseThrow(() -> new EntityNotFoundException("No department found with uuid " + uuid));
    }

    public void delete(String uuid) {
        repository.deleteById(uuid);
    }

    public List<Department> getAll() {
        return IteratorUtils.toList(repository.findAll().iterator());
    }

}
