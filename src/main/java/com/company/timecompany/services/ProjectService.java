package com.company.timecompany.services;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserService userService;

    public List<Project> findAllByCurrentUser() {
        User currentUser = userService.getCurrentUser();
        if (currentUser.isAdmin()) {
            return projectRepository.findAll();
        } else {
            return projectRepository.findByUser(currentUser);
        }
    }
}
