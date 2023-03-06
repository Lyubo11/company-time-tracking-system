package com.company.timecompany.services;

import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.repositories.ProjectRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectRecordService {
    @Autowired
    private ProjectRecordRepository projectRecordRepository;


    public List<ProjectRecord> findAll(String keyword, Integer weekNumber) {
        List<ProjectRecord> recordList = new ArrayList<>();
        if(keyword==null && weekNumber==null){
            recordList = projectRecordRepository.findAll();
        }else if(keyword!=null && weekNumber==null){
            recordList = projectRecordRepository.searchAll(keyword);
        }else if(weekNumber !=null){
            System.out.println(weekNumber);
            recordList =projectRecordRepository.findByWeekNumber(weekNumber);
            System.out.println(recordList);
        }
        return recordList;
    }
}
