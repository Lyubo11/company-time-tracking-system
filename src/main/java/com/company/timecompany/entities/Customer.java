package com.company.timecompany.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 30, message = "Name must be between 4 and 30 characters")
    private String name;
    @OneToMany(mappedBy = "customer")
    private List<Project> projects = new ArrayList<>();

    private String country;

    @FutureOrPresent(message = "Date must be in the future")
    private Date contractExpiration;
}
