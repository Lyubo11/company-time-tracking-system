package com.company.timecompany.entities;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 4, max = 30, message = "Name must be between 4 and 30 characters")
    private String name;
    @NotBlank
    private String description;
    @OneToMany(mappedBy = "project")
    private List<ProjectRecord> projectRecords = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @PastOrPresent(message = "Created date cannot be in the past")
    private Date startDate;
    @FutureOrPresent(message = "Date must be in the future")
    private Date endDate;

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public Project(Integer id, String name, String description, Customer customer, Date startDate, Date endDate) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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

    public Project(Integer id, String name, String description, List<ProjectRecord> projectRecords, Customer customer, Date startDate, Date endDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectRecords = projectRecords;
        this.customer = customer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
