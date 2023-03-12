package com.company.timecompany.utils;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRepository;
import com.company.timecompany.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StatisticsServiceTest {

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private UserService userService;

    @Test
    public void testGetUserProjectHoursNoProjectsReturnsEmptyMap() {
        when(projectRepository.findAll()).thenReturn(Collections.emptyList());

        Map<String, Map<String, Integer>> userProjectHours = statisticsService.getUserProjectHours();

        assertTrue(userProjectHours.isEmpty());
    }
    @Test
    public void testGetUserProjectHours_whenRecordsExist_returnValidData() {
        // Arrange
        User user1 = mock(User.class);
        when(user1.getFirstName()).thenReturn("John");
        User user2 = mock(User.class);
        when(user2.getFirstName()).thenReturn("Jane");

        Project project1 = mock(Project.class);
        when(project1.getName()).thenReturn("Project 1");
        when(project1.getUser()).thenReturn(user1);
        Project project2 = mock(Project.class);
        when(project2.getName()).thenReturn("Project 2");
        when(project2.getUser()).thenReturn(user2);

        ProjectRecord record1 = mock(ProjectRecord.class);
        when(record1.getHoursWorked()).thenReturn(5);
        when(record1.getProject()).thenReturn(project1);
        ProjectRecord record2 = mock(ProjectRecord.class);
        when(record2.getHoursWorked()).thenReturn(10);
        when(record2.getProject()).thenReturn(project2);

        when(project1.getProjectRecords()).thenReturn(Collections.singletonList(record1));
        when(project2.getProjectRecords()).thenReturn(Collections.singletonList(record2));
        when(projectRepository.findAll()).thenReturn(Arrays.asList(project1, project2));

        Map<String, Map<String, Integer>> result = statisticsService.getUserProjectHours();

        Map<String, Map<String, Integer>> expected = new HashMap<>();
        Map<String, Integer> user1ProjectHours = new HashMap<>();
        user1ProjectHours.put("Project 1", 5);
        expected.put("John", user1ProjectHours);
        Map<String, Integer> user2ProjectHours = new HashMap<>();
        user2ProjectHours.put("Project 2", 10);
        expected.put("Jane", user2ProjectHours);
        assertEquals(expected, result);
    }

    @Test
    public void testGetUserProjectHours_whenNoRecordsExist_returnEmptyMap() {

        List<Project> projects = new ArrayList<>();
        User user1 = mock(User.class);
        when(user1.getFirstName()).thenReturn("John");
        Project project1 = mock(Project.class);
        when(project1.getName()).thenReturn("Project 1");
        when(project1.getUser()).thenReturn(user1);
        projects.add(project1);

        User user2 = mock(User.class);
        when(user2.getFirstName()).thenReturn("Jane");
        Project project2 = mock(Project.class);
        when(project2.getName()).thenReturn("Project 2");
        when(project2.getUser()).thenReturn(user2);
        projects.add(project2);

        ProjectRepository projectRepository = mock(ProjectRepository.class);
        when(projectRepository.findAll()).thenReturn(projects);

        Map<String, Map<String, Integer>> result = statisticsService.getUserProjectHours();

        Map<String, Map<String, Integer>> expected = new HashMap<>();
        assertEquals(expected, result);
    }
}