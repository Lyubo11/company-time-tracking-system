package com.company.timecompany.entities;


import com.company.timecompany.constants.ProjectStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.sql.Date;

@Entity
@Table(name = "project_records")
public class ProjectRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Summary cannot be blank")
    @Size(max = 255, message = "Summary must be at most 255 characters")
    @Column(length = 255,nullable = false)
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

    public ProjectRecord(Integer id, String summary) {
        this.id = id;
        this.summary = summary;

    }

    public ProjectRecord() {
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    @Override
    public String toString() {
        return "ProjectRecord{" +
                "id=" + id +
                ", summary='" + summary + '\'' +
                ", status='" + status + '\'' +
                ", project=" + project +
                ", date=" + date +
                ", hoursWorked=" + hoursWorked +
                '}';
    }
}
