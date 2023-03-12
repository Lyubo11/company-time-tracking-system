package com.company.timecompany.services;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

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
    public Project getProjectById(Integer id) {
        Optional<Project> projectOptional = projectRepository.findById(id);
        if (projectOptional.isPresent()) {
            return projectOptional.get();
        } else {
            throw new EntityNotFoundException("Project not found with id: " + id);
        }
    }
}
