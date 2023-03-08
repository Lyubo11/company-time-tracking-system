package com.company.timecompany.services;

import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProjectRecordServiceTest {
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private ProjectRecordService projectRecordService;

    @Mock
    private ProjectRecordRepository projectRecordRepository;


    @Test
    public void testGetAllProjectRecordsWithKeyword() {
        String keyword = "test";
        Integer weekNumber = null;
        List<ProjectRecord> expectedList = new ArrayList<>();
        expectedList.add(new ProjectRecord("Test project"));

        when(projectRecordRepository.searchAll(keyword)).thenReturn(expectedList);

        List<ProjectRecord> actualList = projectRecordService.getAllProjectRecords(keyword, weekNumber, null);

        assertEquals(expectedList, actualList);
        verify(projectRecordRepository, times(1)).searchAll(keyword);
        verify(projectRecordRepository, never()).findAll();
        verify(projectRecordRepository, never()).findByWeekNumber(anyInt());
    }

    @Test
    public void testGetAllProjectRecordsWithWeekNumber() {
        String keyword = null;
        Integer weekNumber = 1;
        List<ProjectRecord> expectedList = new ArrayList<>();
        expectedList.add(new ProjectRecord("Project 1"));

        when(projectRecordRepository.findByWeekNumber(weekNumber)).thenReturn(expectedList);

        List<ProjectRecord> actualList = projectRecordService.getAllProjectRecords(keyword, weekNumber, null);

        assertEquals(expectedList, actualList);
        verify(projectRecordRepository, never()).searchAll(anyString());
        verify(projectRecordRepository, never()).findAll();
        verify(projectRecordRepository, times(1)).findByWeekNumber(weekNumber);
    }
    @Test
    public void testGetAllProjectRecordsWithNoParameters() {
        String keyword = null;
        Integer weekNumber = null;
        List<ProjectRecord> expectedList = new ArrayList<>();
        expectedList.add(new ProjectRecord("Project 1"));
        expectedList.add(new ProjectRecord("Project 2"));

        when(projectRecordRepository.findAll()).thenReturn(expectedList);
        List<ProjectRecord> actualList = projectRecordService.getAllProjectRecords(keyword, weekNumber, null);

        assertEquals(expectedList, actualList);
        verify(projectRecordRepository, never()).searchAll(anyString());
        verify(projectRecordRepository, times(1)).findAll();
        verify(projectRecordRepository, never()).findByWeekNumber(anyInt());
    }
    @Test
    public void testGetUserProjectRecordsWithKeyword() {
        String keyword = "test";
        Integer weekNumber = null;
        User currentUser = new User("testUser");
        List<ProjectRecord> expectedList = new ArrayList<>();
        expectedList.add(new ProjectRecord("Test project"));

        when(projectRecordRepository.searchByProjectUser(currentUser, keyword)).thenReturn(expectedList);

        List<ProjectRecord> actualList = projectRecordService.getUserProjectRecords(keyword, weekNumber, currentUser, null);

        assertEquals(expectedList, actualList);
        verify(projectRecordRepository, times(1)).searchByProjectUser(currentUser, keyword);
        verify(projectRecordRepository, never()).findByProjectUser(any());
        verify(projectRecordRepository, never()).findByWeekNumberAndProjectUser(anyInt(), any());
    }
    @Test
    public void testGetUserProjectRecordsWithWeekNumber() {
        String keyword = null;
        Integer weekNumber = 1;
        User currentUser = new User("testUser");
        List<ProjectRecord> expectedList = new ArrayList<>();
        expectedList.add(new ProjectRecord("Project 1"));

        when(projectRecordRepository.findByWeekNumberAndProjectUser(weekNumber, currentUser)).thenReturn(expectedList);

        List<ProjectRecord> actualList = projectRecordService.getUserProjectRecords(keyword, weekNumber, currentUser, null);

        assertEquals(expectedList, actualList);
        verify(projectRecordRepository, never()).searchByProjectUser(any(), anyString());
        verify(projectRecordRepository, never()).findByProjectUser(any());
        verify(projectRecordRepository, times(1)).findByWeekNumberAndProjectUser(weekNumber, currentUser);
    }
    @Test
    public void testGetUserProjectRecordsWithNoParameters() {
        String keyword = null;
        Integer weekNumber = null;
        User currentUser = new User("testUser");
        List<ProjectRecord> expectedList = new ArrayList<>();
        expectedList.add(new ProjectRecord("Project 1"));
        expectedList.add(new ProjectRecord("Project 2"));

        when(projectRecordRepository.findByProjectUser(currentUser)).thenReturn(expectedList);

        List<ProjectRecord> actualList = projectRecordService.getUserProjectRecords(keyword, weekNumber, currentUser, null);

        assertEquals(expectedList, actualList);
        verify(projectRecordRepository, never()).searchByProjectUser(any(), anyString());
        verify(projectRecordRepository, times(1)).findByProjectUser(currentUser);
        verify(projectRecordRepository, never()).findByWeekNumberAndProjectUser(anyInt(), any());
    }
}