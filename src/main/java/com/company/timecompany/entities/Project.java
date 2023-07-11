package com.company.timecompany.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public Project(String name) {
        this.name = name;
    }

}
