package com.company.timecompany.controllers;

import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.repositories.ProjectRecordRepository;
import com.company.timecompany.repositories.ProjectRepository;
import com.company.timecompany.services.ProjectRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

@Controller
public class ProjectRecordController {

    @Autowired
    private ProjectRecordRepository projectRecordRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private ProjectRecordService projectRecordService;

    //    @GetMapping("/users/{id}/records")
//    public String listProjectRecordsForUser(@PathVariable(name = "id") Integer userId, Model model) {
//        User user = userService.getUserId(userId);
//        List<ProjectRecord> projectRecords = projectRecordRepository.findByUser(user);
//        model.addAttribute("projectRecords", projectRecords);
//        return "project-record/project-records";
//    }
    @GetMapping("users/records")
    public String listAll(Model model, @RequestParam(value = "keyword",required = false) String keyword,
                          @RequestParam(value = "weekNumber",required = false) Integer weekNumber) {
         List<ProjectRecord> listRecords = projectRecordService.findAll(keyword,weekNumber);

        model.addAttribute("listRecords", listRecords);
        model.addAttribute("keyword", keyword);
        model.addAttribute("weekNumber", weekNumber);
        return "project-record/records";
    }
//    @GetMapping("users/records/{weekNumber}/{year}")
//    public String showProjectRecordsByWeek(@PathVariable int weekNumber, @PathVariable int year, Model model) {
//        List<ProjectRecord> projectRecords = projectRecordService.getProjectRecordsByWeek(weekNumber, year);
//        model.addAttribute("projectRecords", projectRecords);
//        return "project-record/records";
//    }

    @GetMapping("users/records/new")
    private String createNewRecord(ProjectRecord projectRecord, Model model) throws ParseException {
        List<Project> listProjects = projectRepository.findAll();
        model.addAttribute("projectRecord", projectRecord);
        model.addAttribute("listProjects", listProjects);
        return "project-record/record-form";
    }

    @PostMapping("users/records/submit")
    private ModelAndView saveProduct(@Valid ProjectRecord projectRecord, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return new ModelAndView("users/records/new");
        } else {
            System.out.println("save");
            projectRecordRepository.save(projectRecord);
            return new ModelAndView("redirect:/users/records");
        }
    }
}
