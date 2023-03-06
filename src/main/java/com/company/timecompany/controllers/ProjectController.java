package com.company.timecompany.controllers;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.repositories.ProjectRecordRepository;
import com.company.timecompany.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProjectController {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectRecordRepository projectRecordRepository;

    @GetMapping("/projects")
    public String listAllCustomers(Model model) {
        List<Project> listProjects = projectRepository.findAll();
        List<ProjectRecord> listProjectRecords = projectRecordRepository.findAll();
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("listProjectRecords", listProjectRecords);
        return "/project/projects";
    }
//
//    @GetMapping("/projects/new")
//    private String createNewProduct(Project project, Model model) {
//        List<Project> listProjects = projectRepository.findAll();
//
//        model.addAttribute("project", project);
//        model.addAttribute("listProjects", listProjects);
//        return "project/project-form";
//    }
//
//    @PostMapping("/projects/submit")
//    private ModelAndView saveProduct(@Valid Project project, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            System.out.println("error");
//            return new ModelAndView("projects/new");
//        } else {
//            System.out.println("save");
//            projectRepository.save(project);
//            return new ModelAndView("redirect:/projects/");
//        }
//    }
//
//    @GetMapping("/projects/edit/{id}")
//    private String editProduct(@PathVariable("id") Integer id, Model model) {
//        Project project = projectRepository.findById(id).get();
//        List<Project> listProjects = projectRepository.findAll();
//        model.addAttribute("project", project);
//        model.addAttribute("listProjects", listProjects);
//        return "project/project-form";
//    }
//
//    @GetMapping("/projects/delete/{id}")
//    private ModelAndView deleteProduct(@PathVariable("id") Integer id, Model model) {
//        projectRepository.deleteById(id);
//        return new ModelAndView("redirect:/projects");
//    }
}
