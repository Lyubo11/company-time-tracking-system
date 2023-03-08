package com.company.timecompany.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "project")
    private List<ProjectRecord> projectRecords = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "user_id")
private User user;
     private String startDate;
    private String endDate;

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Project(Integer id, String name, String description, Customer customer, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<ProjectRecord> getProjectRecords() {
        return projectRecords;
    }

    public void setProjectRecords(List<ProjectRecord> projectRecords) {
        this.projectRecords = projectRecords;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project(Integer id, String name, String description, List<ProjectRecord> projectRecords, Customer customer, String startDate, String endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectRecords = projectRecords;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
