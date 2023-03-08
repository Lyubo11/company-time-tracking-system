package com.company.timecompany.services;

import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectRecordService {
    @Autowired
    private ProjectRecordRepository projectRecordRepository;


//    public List<ProjectRecord> findAll(String keyword, Integer weekNumber) {
//        List<ProjectRecord> recordList = new ArrayList<>();
//        if(keyword==null && weekNumber==null){
//            recordList = projectRecordRepository.findAll();
//        }else if(keyword!=null && weekNumber==null){
//            recordList = projectRecordRepository.searchAll(keyword);
//        }else if(weekNumber !=null){
//            System.out.println(weekNumber);
//            recordList =projectRecordRepository.findByWeekNumber(weekNumber);
//            System.out.println(recordList);
//        }
//        return recordList;
//    }
public List<ProjectRecord> findAll(String keyword, Integer weekNumber, User currentUser) {
    List<ProjectRecord> recordList = new ArrayList<>();
    if(currentUser.isAdmin()) {
        // User is an admin, fetch all records
        recordList = getAllProjectRecords(keyword, weekNumber, recordList);
    } else {
        // User is not an admin, fetch only their own records
        recordList = getUserProjectRecords(keyword, weekNumber, currentUser, recordList);
    }
    return recordList;
}

    List<ProjectRecord> getUserProjectRecords(String keyword, Integer weekNumber, User currentUser, List<ProjectRecord> recordList) {
        if (keyword == null && weekNumber == null) {
            recordList = projectRecordRepository.findByProjectUser(currentUser);
        } else if (keyword != null && weekNumber == null) {
            recordList = projectRecordRepository.searchByProjectUser(currentUser, keyword);
        } else if (weekNumber != null) {
            recordList = projectRecordRepository.findByWeekNumberAndProjectUser(weekNumber, currentUser);
        }
        return recordList;
    }

    List<ProjectRecord> getAllProjectRecords(String keyword, Integer weekNumber, List<ProjectRecord> recordList) {
        if(keyword == null && weekNumber == null){
            recordList = projectRecordRepository.findAll();
        } else if(keyword != null && weekNumber == null){
            recordList = projectRecordRepository.searchAll(keyword);
        } else if(weekNumber != null){
            recordList = projectRecordRepository.findByWeekNumber(weekNumber);
        }
        return recordList;
    }
}
