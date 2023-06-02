package com.company.timecompany.services;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    private final UserService userService;

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
