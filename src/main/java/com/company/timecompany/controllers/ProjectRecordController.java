package com.company.timecompany.controllers;

import com.company.timecompany.constants.ProjectStatus;
import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.ProjectRecordRepository;
import com.company.timecompany.repositories.ProjectRepository;
import com.company.timecompany.repositories.UserRepository;
import com.company.timecompany.services.ProjectRecordService;
import com.company.timecompany.services.ProjectService;
import com.company.timecompany.utils.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ProjectRecordController {

    private final ProjectRecordRepository projectRecordRepository;

    private final ProjectRepository projectRepository;
    private final ProjectRecordService projectRecordService;
    private final UserRepository userRepository;
    private final ProjectService projectService;
    private final StatisticsService statisticsService;

    @GetMapping("records")
    public String listAll(Model model, @RequestParam(value = "keyword", required = false) String keyword,
                          @RequestParam(value = "weekNumber", required = false) Integer weekNumber, Principal principal) {
        User currentUser = userRepository.getUserByUsername(principal.getName());
        List<ProjectRecord> listRecords = projectRecordService.findAll(keyword, weekNumber, currentUser);
        List<Project> listProjects = projectService.findAllByCurrentUser();
        model.addAttribute("listRecords", listRecords);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("keyword", keyword);
        model.addAttribute("weekNumber", weekNumber);
        return "project-record/records";
    }

    @GetMapping("records/new")
    public String createNewRecord(ProjectRecord projectRecord, Model model) throws ParseException {
        List<Project> listProjects = projectService.findAllByCurrentUser();
        model.addAttribute("listStatuses", ProjectStatus.values());
        model.addAttribute("projectRecord", projectRecord);
        model.addAttribute("listProjects", listProjects);
        model.addAttribute("pageTitle", "Create New Record");
        return "project-record/record-form";
    }

    @PostMapping("records/submit")
    public ModelAndView saveProduct(@Valid ProjectRecord projectRecord, Model model, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("listStatuses", ProjectStatus.values());
            List<Project> listProjects = projectService.findAllByCurrentUser();
            model.addAttribute("listProjects", listProjects);

            return new ModelAndView("project-record/record-form");
        } else {
            projectRecordRepository.save(projectRecord);
            redirectAttributes.addFlashAttribute("message", "The record has been saved successfully.");
            return new ModelAndView("redirect:/records");
        }
    }

    @GetMapping("/records/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Optional<ProjectRecord> projectRecord = projectRecordRepository.findById(id);
        List<ProjectRecord> listRecords = projectRecordRepository.findAll();
        List<Project> listProjects = projectRepository.findAll();
        model.addAttribute("listStatuses", ProjectStatus.values());
        model.addAttribute("projectRecord", projectRecord);
        model.addAttribute("listRecords", listRecords);
        model.addAttribute("listProjects", listProjects);

        model.addAttribute("pageTitle", "Edit record (ID: " + id + ")");
        return "project-record/record-form";
    }

    @GetMapping("/records/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Integer id, Model model) {
        projectRecordRepository.deleteById(id);
        return new ModelAndView("redirect:/records");
    }

    @GetMapping("/records/statistics")
    public String showHoursWorked(Model model) {
        Map<String, Map<String, Integer>> userProjectHours = statisticsService.getUserProjectHours();
        model.addAttribute("userProjectHours", userProjectHours);
        return "project-record/statistics";
    }
}
