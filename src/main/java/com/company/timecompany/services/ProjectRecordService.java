package com.company.timecompany.services;

import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProjectRecordService {
    private final ProjectRecordRepository projectRecordRepository;

    public List<ProjectRecord> findAll(String keyword, Integer weekNumber, User currentUser) {
        List<ProjectRecord> recordList = new ArrayList<>();
        if (currentUser.isAdmin()) {
            recordList = getAllProjectRecords(keyword, weekNumber, recordList);
        } else {
            recordList = getUserProjectRecords(keyword, weekNumber, currentUser, recordList);
        }
        return recordList;
    }

    List<ProjectRecord> getUserProjectRecords(String keyword, Integer weekNumber, User currentUser, List<ProjectRecord> recordList) {
        if (keyword == null && weekNumber == null) {
            recordList = projectRecordRepository.findByProjectUser(currentUser);
        } else if (keyword != null && weekNumber == null) {
            recordList = projectRecordRepository.searchByProjectUser(currentUser, keyword);
        } else if (weekNumber != 0) {
            recordList = projectRecordRepository.findByWeekNumberForProjectUser(weekNumber, currentUser);
        }
        return recordList;
    }

    List<ProjectRecord> getAllProjectRecords(String keyword, Integer weekNumber, List<ProjectRecord> recordList) {
        if (keyword == null && weekNumber == null) {
            recordList = projectRecordRepository.findAll();
        } else if (keyword != null && weekNumber == null) {
            recordList = projectRecordRepository.searchAll(keyword);
        } else if (weekNumber != 0) {
            recordList = projectRecordRepository.findByWeekNumber(weekNumber);
        }
        return recordList;
    }
}
