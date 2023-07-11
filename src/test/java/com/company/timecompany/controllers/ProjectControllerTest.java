package com.company.timecompany.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.company.timecompany.entities.Customer;
import com.company.timecompany.entities.Project;
import com.company.timecompany.entities.ProjectRecord;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.CustomerRepository;
import com.company.timecompany.repositories.ProjectRepository;
import com.company.timecompany.repositories.UserRepository;
import com.company.timecompany.services.ProjectRecordService;
import com.company.timecompany.services.ProjectService;
import com.company.timecompany.services.UserService;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@ExtendWith(MockitoExtension.class)
class ProjectControllerTest {

    @Mock ProjectRecordService projectRecordService;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private ProjectService projectService;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private ProjectController projectController;
    @Mock
    private UserRepository userRepository;
    @Mock
    private BindingResult bindingResult;

    @Mock
    private Model model;

    @Test
    @DisplayName("Should list all projects")
    void testShouldGetAllProjects() {
        User testUser = User.builder().firstName("ivan").build();
        List<Project> listProjects = new ArrayList<>();
        List<ProjectRecord> listProjectRecords = new ArrayList<>();
        List<User> listUsers = new ArrayList<>();
        Principal principalUser = mock(Principal.class);

        when(userRepository.getUserByUsername(any())).thenReturn(testUser);
        when(projectService.findAllByCurrentUser()).thenReturn(listProjects);
        when(projectRecordService.findAll(any(), any(), any())).thenReturn(listProjectRecords);
        when(userRepository.findAll()).thenReturn(listUsers);

        String result = projectController.listAllProjects(model, "one", 1, principalUser);

        assertEquals("/project/projects",result);

    }

    @Test
    @DisplayName("Should create new project")
    void testSouldCreateNewProject() {
        Project project = new Project();
        List<Project> listProjects = new ArrayList<>();
        List<Customer> listCustomers = new ArrayList<>();
        List<User> listUsers = new ArrayList<>();

        when(projectService.findAllByCurrentUser()).thenReturn(listProjects);
        when(customerRepository.findAll()).thenReturn(listCustomers);
        when(userService.listAllEmployees()).thenReturn(listUsers);

        String result = projectController.createNewProject(project, model);

        assertEquals( "project/project-form",result);

    }

    @Test
    @DisplayName("Should save the project when the binding result has no errors")
    void testSaveProjectWhenBindingResultHasNoErrors() {
        Project project = new Project("Test Project");

        when(bindingResult.hasErrors()).thenReturn(false);

        ModelAndView modelAndView = projectController.saveProject(model, project, bindingResult);

        verify(projectRepository, times(1)).save(project);
        assertEquals("redirect:/projects", modelAndView.getViewName());
    }

    @Test
    @DisplayName("Should not save the project and return to form when the binding result has errors")
    void testSaveProjectWhenBindingResultHasErrors() {
        Project project = new Project("Test Project");
        List<Customer> listCustomers = new ArrayList<>();
        List<User> listUsers = new ArrayList<>();

        when(bindingResult.hasErrors()).thenReturn(true);
        when(customerRepository.findAll()).thenReturn(listCustomers);
        when(userService.listAllEmployees()).thenReturn(listUsers);

        ModelAndView modelAndView = projectController.saveProject(model, project, bindingResult);

        verify(projectRepository, never()).save(project);
        verify(model).addAttribute("listCustomers", listCustomers);
        verify(model).addAttribute("listUsers", listUsers);

        assertEquals("project/project-form", modelAndView.getViewName());
    }

    @Test
    @DisplayName("Should edit project by having project id")
    void testShouldEditProject() {
        int findId = 1;
        Project project = Project.builder().id(findId).build();
        User testUser = User.builder().username("Ivan").build();
        List<Project> listProjects = new ArrayList<>();
        List<Customer> listCustomers = new ArrayList<>();
        Principal principalUser = mock(Principal.class);

        when(projectService.getProjectById(findId)).thenReturn(project);
        when(userRepository.getUserByUsername(any())).thenReturn(testUser);
        when(projectService.findAllByCurrentUser()).thenReturn(listProjects);
        when(customerRepository.findAll()).thenReturn(listCustomers);

        String result = projectController.editProject(findId, model, principalUser);

        assertEquals("project/project-form", result);
    }

    @Test
    @DisplayName("Should delete project by having project id")
    void testShouldDeleteProject() {
        int id = 1;

        ModelAndView modelAndView = projectController.deleteProject(id,model);

        assertEquals("redirect:/projects", modelAndView.getViewName());

    }
}