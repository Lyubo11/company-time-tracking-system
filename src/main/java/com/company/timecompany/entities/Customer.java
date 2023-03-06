package com.company.timecompany.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String country;
    @OneToMany(mappedBy = "customer")
    private List<Project> projects = new ArrayList<>();

    private Date contractExpiration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public Date getContractExpiration() {
        return contractExpiration;
    }

    public void setContractExpiration(Date contractExpiration) {
        this.contractExpiration = contractExpiration;
    }
}
