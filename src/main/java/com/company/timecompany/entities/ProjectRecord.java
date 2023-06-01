package com.company.timecompany.entities;


import com.company.timecompany.constants.ProjectStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_records")
public class ProjectRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Summary cannot be blank")
    @Size(max = 255, message = "Summary must be at most 255 characters")
    @Column(nullable = false)
    private String summary;
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @PastOrPresent(message = "Date must be present")
    private Date date;
    private int hoursWorked;

    public ProjectRecord(String summary) {
        this.summary = summary;
    }
}
