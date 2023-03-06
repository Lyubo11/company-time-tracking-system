package com.company.timecompany.services;

import com.company.timecompany.entities.ProjectRecord;
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
    public void testFindAllIfNoFilterCriterias() {
        List<ProjectRecord> testData = new ArrayList<>();
        testData.add(new ProjectRecord());
        testData.add(new ProjectRecord());

        when(projectRecordRepository.findAll()).thenReturn(testData);
        List<ProjectRecord> result = projectRecordService.findAll(null, null);
        assertEquals(testData, result);
    }
    @Test
    public void testFindAllWithKeyword() {
        String keyword = "test";
        List<ProjectRecord> expectedRecords = new ArrayList<>();
        expectedRecords.add(new ProjectRecord("Test Project 1"));
        expectedRecords.add(new ProjectRecord("Test Project 2"));

        when(projectRecordRepository.searchAll(keyword)).thenReturn(expectedRecords);
        List<ProjectRecord> actualRecords = projectRecordService.findAll(keyword, null);
        assertEquals(expectedRecords, actualRecords);
        verify(projectRecordRepository, times(1)).searchAll(keyword);
    }
    @Test
    public void testFindAllByWeekNumber() {
        List<ProjectRecord> testList = new ArrayList<>();
        testList.add(new ProjectRecord(1, "Project 1"));
        testList.add(new ProjectRecord(2, "Project 2"));
        when(projectRecordRepository.findByWeekNumber(1)).thenReturn(testList);

        List<ProjectRecord> result = projectRecordService.findAll(null, 1);
        assertEquals(testList, result);
        verify(projectRecordRepository, times(1)).findByWeekNumber(1);
    }
}