package com.company.timecompany.controllers;

import com.company.timecompany.entities.Role;
import com.company.timecompany.repositories.RoleRepository;
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
public class RoleController {
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/roles")
    private String listAllRoles(Model model) {
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        return "role/roles";
    }

    @GetMapping("/roles/new")
    private String createNewRole(Role role, Model model) {
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("role", role);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New Role");
        return "role/role-form";
    }

    @PostMapping("/roles/submit")
    private ModelAndView saveRole(@Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("role/roles/new");
        } else {
            roleRepository.save(role);
            redirectAttributes.addFlashAttribute("message", "The role has been saved successfully.");

            return new ModelAndView("redirect:/roles");
        }
    }

    @GetMapping("/roles/edit/{id}")
    private String editRole(@PathVariable("id") Integer id, Model model) {
        Role role = roleRepository.findById(id).get();
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("role", role);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Edit role (ID: " + id + ")");
        return "role/role-form";
    }

    @GetMapping("/roles/delete/{id}")
    private ModelAndView deleteProduct(@PathVariable("id") Integer id, Model model) {
        roleRepository.deleteById(id);
        return new ModelAndView("redirect:/roles");
    }
}
