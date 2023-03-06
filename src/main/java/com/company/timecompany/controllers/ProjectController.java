package com.company.timecompany.controllers;

import com.company.timecompany.entities.Customer;
import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.repositories.CustomerRepository;
import com.company.timecompany.repositories.ProjectRecordRepository;
import com.company.timecompany.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProjectRecordRepository projectRecordRepository;

    @GetMapping("/projects")
    public String listAllProjects(Model model) {
        List<Project> listProjects = projectRepository.findAll();
        List<ProjectRecord> listProjectRecords = projectRecordRepository.findAll();
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listProjectRecords", listProjectRecords);
        return "/project/projects";
    }

    @GetMapping("/projects/new")
    private String createNewProject(Project project, Model model) {
        List<Project> listProjects = projectRepository.findAll();
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("project", project);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listCustomers", listCustomers);
        return "project/project-form";
    }

    @PostMapping("/projects/submit")
    private ModelAndView saveProject(@Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return new ModelAndView("projects/new");
        } else {
            System.out.println("save");
            projectRepository.save(project);
            return new ModelAndView("redirect:/projects/");
        }
    }

    @GetMapping("/projects/edit/{id}")
    private String editProject(@PathVariable("id") Integer id, Model model) {
        Project project = projectRepository.findById(id).get();
        List<Project> listProjects = projectRepository.findAll();
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("project", project);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listCustomers", listCustomers);
        return "project/project-form";
    }

    @GetMapping("/projects/delete/{id}")
    private ModelAndView deleteProject(@PathVariable("id") Integer id, Model model) {
        projectRepository.deleteById(id);
        return new ModelAndView("redirect:/projects");
    }
}
