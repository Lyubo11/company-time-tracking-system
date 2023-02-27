package com.company.timecompany.controllers;

import com.company.timecompany.entities.Role;
import com.company.timecompany.entities.User;
import com.company.timecompany.repositories.RoleRepository;
import com.company.timecompany.repositories.UserRepository;
import com.company.timecompany.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        return "/user/user-form";
    }

    @PostMapping("/users/submit")
    public ModelAndView saveUser(@Valid User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/users/new");
        } else {
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("message", "The user has been saved succesfully.");
            return new ModelAndView("redirect:/users");
        }
    }
}
