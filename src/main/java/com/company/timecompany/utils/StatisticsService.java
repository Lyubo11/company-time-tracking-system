package com.company.timecompany.utils;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
    @Service
    public class StatisticsService {
        @Autowired
        private ProjectRepository projectRepository;


        public Map<String, Map<String, Integer>> getUserProjectHours() {
            List<Project> projects = projectRepository.findAll();
            Map<String, Map<String, Integer>> userProjectHours = new HashMap<>();

            for (Project project : projects) {
                for (ProjectRecord record : project.getProjectRecords()) {
                    String usefirstNamename = record.getProject().getUser().getFirstName();
                    String projectName = project.getName();
                    int hours = record.getHoursWorked();

                    Map<String, Integer> projectHours = userProjectHours.get(usefirstNamename);
                    if (projectHours == null) {
                        projectHours = new HashMap<>();
                        userProjectHours.put(usefirstNamename, projectHours);
                    }
                    projectHours.put(projectName, projectHours.getOrDefault(projectName, 0) + hours);
                }
            }
            return userProjectHours;
        }
    }

