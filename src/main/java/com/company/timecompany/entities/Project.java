package com.company.timecompany.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
    public class Project {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;

        private String projectName;
        private String description;
        @OneToMany(mappedBy = "project")
        private List<ProjectRecord> projectRecords = new ArrayList<>();
        @ManyToOne
        @JoinColumn(name = "customer_id")
        private Customer customer;

        private String startDate;
        private String endDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    @Override
    public String toString() {
        return this.id.toString();
    }

    public void setProjectRecords(List<ProjectRecord> projectRecords) {
        this.projectRecords = projectRecords;


    }
}
