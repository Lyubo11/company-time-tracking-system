package com.company.timecompany.utils;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class StatisticsService {
    private final ProjectRepository projectRepository;


    public Map<String, Map<String, Integer>> getUserProjectHours() {
        List<Project> projects = projectRepository.findAll();
        Map<String, Map<String, Integer>> userProjectHours = new HashMap<>();

        for (Project project : projects) {
            for (ProjectRecord projectRecord : project.getProjectRecords()) {
                String userFirstName = projectRecord.getProject().getUser().getFirstName();
                String projectName = project.getName();
                int hours = projectRecord.getHoursWorked();

                Map<String, Integer> projectHours = userProjectHours.computeIfAbsent(userFirstName, k -> new HashMap<>());
                if (!projectHours.containsKey(projectName)) {
                    projectHours.put(projectName, hours);
                } else {
                    projectHours.put(projectName, projectHours.get(projectName) + hours);
                }
            }
        }
        return userProjectHours;
    }
}

