package com.company.timecompany.entities;


import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "project_records")
public class ProjectRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String summary;
    private String status;
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    private Date date;
    private int hoursWorked;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
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
