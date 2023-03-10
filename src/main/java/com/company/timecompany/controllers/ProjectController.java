package com.company.timecompany.controllers;

import com.company.timecompany.entities.Customer;
import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.CustomerRepository;
import com.company.timecompany.repositories.ProjectRecordRepository;
import com.company.timecompany.repositories.ProjectRepository;
import com.company.timecompany.repositories.UserRepository;
import com.company.timecompany.services.ProjectService;
import com.company.timecompany.services.UserService;
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
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectRecordRepository projectRecordRepository;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/projects")
    public String listAllProjects(Model model) {
        List<Project> listProjects = projectService.findAllByCurrentUser();
        List<ProjectRecord> listProjectRecords = projectRecordRepository.findAll();
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listProjectRecords", listProjectRecords);
        model.addAttribute("listUsers", listUsers);
        return "/project/projects";
    }

    @GetMapping("/projects/new")
    private String createNewProject(Project project, Model model) {
//        List<Project> listProjects = projectRepository.findAll();
        List<Project> listProjects = projectService.findAllByCurrentUser();
        List<Customer> listCustomers = customerRepository.findAll();
        List<User> listUsers = userService.listAllEmployees();
//        List<User> listUsers = userRepository.findAll();

        model.addAttribute("project", project);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("pageTitle", "New Project");
        return "project/project-form";
    }

    @PostMapping("/projects/submit")
    private ModelAndView saveProject(@Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return new ModelAndView("project/project-form");
        } else {
            System.out.println("save");
            projectRepository.save(project);
            return new ModelAndView("redirect:/projects/");
        }
    }

    @GetMapping("/projects/edit/{id}")
    private String editProject(@PathVariable("id") Integer id, Model model) {
        Project project = projectRepository.findById(id).get();
//        List<Project> listProjects = projectRepository.findAll();
        List<Project> listProjects = projectService.findAllByCurrentUser();
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("project", project);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("pageTitle", "Edit Project (ID: " + id + ")");
        return "project/project-form";
    }

    @GetMapping("/projects/delete/{id}")
    private ModelAndView deleteProject(@PathVariable("id") Integer id, Model model) {
        projectRepository.deleteById(id);
        return new ModelAndView("redirect:/projects");
    }
}
