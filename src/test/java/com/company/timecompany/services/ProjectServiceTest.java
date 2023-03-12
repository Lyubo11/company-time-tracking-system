package com.company.timecompany.services;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.repositories.ProjectRepository;
import com.company.timecompany.utils.StatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class ProjectServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private ProjectService projectService;

    @Test
    public void testGetUserProjectHoursNoProjectsReturnsEmptyMap() {
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());

        Map<String, Map<String, Integer>> userProjectHours = statisticsService.getUserProjectHours();

        assertTrue(userProjectHours.isEmpty());
    }
    @Test
    public void testAddHoursToExistingProject() {
        Map<String, Map<String, Integer>> userProjectHours = new HashMap<>();
        String userFirstName = "John";
        String projectName = "Project A";
        int hours1 = 10;
        int hours2 = 20;

        Project project = new Project();
        project.setName(projectName);
        ProjectRecord projectRecord1 = new ProjectRecord();
        projectRecord1.setProject(project);
        projectRecord1.setHoursWorked(hours1);
        project.getProjectRecords().add(projectRecord1);
        ProjectRecord projectRecord2 = new ProjectRecord();
        projectRecord2.setProject(project);
        projectRecord2.setHoursWorked(hours2);
        project.getProjectRecords().add(projectRecord2);

        List<Project> projects = new ArrayList<>();
        projects.add(project);

        Mockito.when(projectRepository.findAll()).thenReturn(projects);

        userProjectHours = statisticsService.getUserProjectHours();

        assertEquals(userProjectHours.size(), 1);
        assertTrue(userProjectHours.containsKey(userFirstName));

        Map<String, Integer> projectHours = userProjectHours.get(userFirstName);
        assertEquals(projectHours.size(), 1);
        assertTrue(projectHours.containsKey(projectName));
        assertEquals(projectHours.get(projectName), hours1+hours2);
    }

}

