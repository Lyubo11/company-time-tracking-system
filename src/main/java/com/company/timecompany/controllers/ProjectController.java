package com.company.timecompany.controllers;

import com.company.timecompany.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;
}
