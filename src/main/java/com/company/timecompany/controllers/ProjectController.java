package com.company.timecompany.controllers;

import com.company.timecompany.entities.Project;
import com.company.timecompany.repositories.ProjectRepository;
import com.company.timecompany.repositories.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/projects")
    public String listAllProjects(Model model) {
        List<Project> listProjects = projectRepository.findAll();
        model.addAttribute("listProjects", listProjects);
        return "project/projects";
    }

    @GetMapping("/projects/new")
    public String newProject(Project project, User user, Model model) {
        List<Project> projectList = projectRepository.findAll();
        List<com.company.timecompany.entities.User> userList = userRepository.findAll();
        model.addAttribute("project", project);
        model.addAttribute("projectList", projectList);
        model.addAttribute("userList", userList);
        return "/project/project-form";
    }

    @PostMapping("/projects/submit")
    public ModelAndView saveProject(@Valid Project project, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/projects/new");
        } else {
            projectRepository.save(project);
            redirectAttributes.addFlashAttribute("message", "The project has been saved successfully!");
            return new ModelAndView("redirect:/projects");
        }
    }

    @GetMapping("/projects/create")
    private String createProject(Project project) {
        return "project/project-form";
    }

    @PostMapping("/projects/submit")
    private String saveProject(@Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/projects/create";
        }
        projectRepository.save(project);
        return "redirect:/projects";
    }

    @GetMapping("/projects/edit/{projectId}")
    private String editProject(@PathVariable(name = "projectId") Integer projectId, Model model) {
        model.addAttribute("project", projectRepository.findById(projectId));
        return "/project/edit";
    }

    @PostMapping("/update")
    private String updateProject(@Valid Project project, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/project/edit";
        }
        projectRepository.save(project);
        return "redirect:/project/projects";
    }

    @PostMapping("/delete/{projectId}")
    private String deleteCustomer(@PathVariable(name = "projectId") Integer projectId) {
        projectRepository.deleteById(projectId);
        return "redirect:/projects";
    }
}
