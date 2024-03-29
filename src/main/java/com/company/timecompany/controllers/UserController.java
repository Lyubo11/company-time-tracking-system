package com.company.timecompany.controllers;

import com.company.timecompany.entities.Role;
import com.company.timecompany.entities.User;
import com.company.timecompany.exceptions.UserNotFoundException;
import com.company.timecompany.repositories.RoleRepository;
import com.company.timecompany.services.UserService;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @GetMapping("/users")
    public String listAll(Model model) {
        List<User> listUsers = userService.listAllUsers();
        model.addAttribute("listUsers", listUsers);
        return "user/users";
    }

    @GetMapping("/users/new")
    public String newUser(User user, Model model) {
        List<Role> listRoles = roleRepository.findAll();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New User");
        return "/user/user-form";
    }

    @PostMapping("/users/submit")
    public ModelAndView saveUser(@Valid User user, Model model, BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            List<Role> listRoles = roleRepository.findAll();
            model.addAttribute("listRoles", listRoles);
            return new ModelAndView("user/user-form");
        } else {
            user.setCreatedAt(Date.valueOf(LocalDate.now()));
            if (!userService.isUsernameUnique(user.getUsername(), user.getId())) {
                bindingResult.rejectValue("username", "error.user", "Username already exists");
                return new ModelAndView("user/user-form");
            }
            userService.save(user);
            redirectAttributes.addFlashAttribute("message", "The user has been saved successfully.");
            return new ModelAndView("redirect:/users");
        }
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes, Model model) {
        try {
            User user = userService.getUserId(id);
            List<Role> listRoles = roleRepository.findAll();

            model.addAttribute("user", user);
            model.addAttribute("listRoles", listRoles);
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");

            return "user/user-form";
        } catch (UserNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/user/users";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        try {
            userService.deleteUser(id);
            redirectAttributes.addFlashAttribute("message", "The user ID:" + id + " has been deleted successfully");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enabled/{status}")
    public String updateUserEnabledStatus(@PathVariable("id") Integer id, @PathVariable("status") boolean enabled, RedirectAttributes redirectAttributes) {
        userService.updateUserEnabledStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The User ID " + id + " has been " + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }
}