package com.company.timecompany.services;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.aspectj.bridge.MessageUtil.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProjectServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private UserService userService;

    @InjectMocks
    private ProjectService projectService;

    @Test
    public void testFindAllByAdminUser() {
        User adminUser = mock(User.class);
        when(userService.getCurrentUser()).thenReturn(adminUser);
        when(adminUser.isAdmin()).thenReturn(true);
        List<Project> allProjects = Arrays.asList(new Project(), new Project());
        when(projectRepository.findAll()).thenReturn(allProjects);

        List<Project> result = projectService.findAllByCurrentUser();

        assertEquals(allProjects, result);
        verify(userService, times(1)).getCurrentUser();
        verify(adminUser, times(1)).isAdmin();
        verify(projectRepository, times(1)).findAll();
    }


    @Test
    public void testGetProjectById() {

        Project project = new Project();
        when(projectRepository.findById(1)).thenReturn(Optional.of(project));
        Project result = projectService.getProjectById(1);
        assertEquals(project, result);

        when(projectRepository.findById(2)).thenReturn(Optional.empty());
        try {
            projectService.getProjectById(2);
            fail("Expected EntityNotFoundException to be thrown");
        } catch (EntityNotFoundException e) {
            assertEquals("Project not found with id: 2", e.getMessage());
        }
    }



}

