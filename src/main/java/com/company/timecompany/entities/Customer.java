package com.company.timecompany.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @OneToMany(mappedBy = "customer")
    private List<Project> projects = new ArrayList<>();

    private String country;

    private Date contractExpiration;

    public Customer() {
    }

    public Customer(Integer id, String name, List<Project> projects, String country, Date contractExpiration) {
        this.id = id;
        this.name = name;
        this.projects = projects;
        this.country = country;
        this.contractExpiration = contractExpiration;
    }

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

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", projects=" + projects +
                ", country='" + country + '\'' +
                ", contractExpiration=" + contractExpiration +
                '}';
    }
}
