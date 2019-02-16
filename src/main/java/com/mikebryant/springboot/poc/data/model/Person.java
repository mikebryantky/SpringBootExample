package com.mikebryant.springboot.poc.data.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "uuid", nullable = false, length = 36)
    private String uuid;

    @Basic
    @Column(name = "first_name", nullable = false, length = 150)
    @NotNull(message = "First name is required.")
    @Size(min = 1, max = 150, message = "First Name must be between 1 and 150 characters.")
    private String firstName;

    @Basic
    @Column(name = "last_name", nullable = false, length = 150)
    @NotNull(message = "Last name is required.")
    @Size(min = 1, max = 150, message = "Last Name must be between 1 and 150 characters.")
    private String lastName;

    @Basic
    @Column(name = "birth_date", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Birthdate is required.")
    private LocalDate birthDate;

    @Basic
    @Column(name = "email_address", nullable = true, length = 255)
    @Email(message = "Invalid email address.")
    @Size(min = 1, max = 255, message = "Email address cannot be more than 255 characters.")
    private String emailAddress;

    @ManyToOne
    @JoinColumn(name = "department_uuid", referencedColumnName = "uuid", nullable = false)
    @NotNull(message = "Department is required.")
    private Department department;

}
