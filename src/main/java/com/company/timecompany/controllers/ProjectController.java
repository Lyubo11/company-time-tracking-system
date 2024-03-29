package com.company.timecompany.controllers;

import com.company.timecompany.entities.Customer;
import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.CustomerRepository;
import com.company.timecompany.repositories.ProjectRecordRepository;
import com.company.timecompany.repositories.ProjectRepository;
import com.company.timecompany.repositories.UserRepository;
import com.company.timecompany.services.ProjectRecordService;
import com.company.timecompany.services.ProjectService;
import com.company.timecompany.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final ProjectRecordRepository projectRecordRepository;
    private final ProjectService projectService;
    private final ProjectRecordService projectRecordService;

    @GetMapping("/projects")
    public String listAllProjects(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                                  @RequestParam(value = "weekNumber", required = false) Integer weekNumber, Principal principal) {
        User currentUser = userRepository.getUserByUsername(principal.getName());
        List<Project> listProjects = projectService.findAllByCurrentUser();
        List<ProjectRecord> listProjectRecords = projectRecordService.findAll(keyword, weekNumber, currentUser);
        List<User> listUsers = userRepository.findAll();
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listProjectRecords", listProjectRecords);
        model.addAttribute("listUsers", listUsers);
        return "/project/projects";
    }

    @GetMapping("/projects/new")
    public String createNewProject(Project project, Model model) {
        List<Project> listProjects = projectService.findAllByCurrentUser();
        List<Customer> listCustomers = customerRepository.findAll();
        List<User> listUsers = userService.listAllEmployees();

        model.addAttribute("project", project);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("pageTitle", "New Project");
        return "project/project-form";
    }

    @PostMapping("/projects/submit")
    public ModelAndView saveProject(Model model, @Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Customer> listCustomers = customerRepository.findAll();
            List<User> listUsers = userService.listAllEmployees();
            model.addAttribute("listCustomers", listCustomers);
            model.addAttribute("listUsers", listUsers);
            return new ModelAndView("project/project-form");
        } else {
            projectRepository.save(project);
            return new ModelAndView("redirect:/projects");
        }
    }

    @GetMapping("/projects/edit/{id}")
    public String editProject(@PathVariable("id") Integer id, Model model, Principal principal) {
        Project project = projectService.getProjectById(id);
        User currentUser = userRepository.getUserByUsername(principal.getName());
        List<Project> listProjects = projectService.findAllByCurrentUser();
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("project", project);
        model.addAttribute("listUsers", currentUser);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("pageTitle", "Edit Project (ID: " + id + ")");
        return "project/project-form";
    }

    @GetMapping("/projects/delete/{id}")
    public ModelAndView deleteProject(@PathVariable("id") Integer id, Model model) {
        projectRepository.deleteById(id);
        return new ModelAndView("redirect:/projects");
    }
}
