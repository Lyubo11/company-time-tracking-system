package com.company.timecompany.repositories;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Integer> {
    List<Project> findByUser(User user);
}
