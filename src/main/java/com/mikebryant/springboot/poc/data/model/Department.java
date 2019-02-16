package com.mikebryant.springboot.poc.data.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
public class Department {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;

    @Basic
    @NotNull(message = "Name is required.")
    @Size(min = 1, max = 150, message = "Name must be between 1 and 150 characters.")
    @Column(name = "name", nullable = false, length = 150)
    private String name;


    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private Set<Person> people;

}
