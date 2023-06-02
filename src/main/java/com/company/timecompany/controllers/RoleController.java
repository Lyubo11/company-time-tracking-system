package com.company.timecompany.controllers;

import com.company.timecompany.entities.Role;
import com.company.timecompany.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
@Controller
public class RoleController {
    private final RoleRepository roleRepository;

    @GetMapping("/roles")
    public String listAllRoles(Model model) {
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("listRoles", listRoles);
        return "role/roles";
    }

    @GetMapping("/roles/new")
    public String createNewRole(Role role, Model model) {
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("role", role);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Create New Role");
        return "role/role-form";
    }

    @PostMapping("/roles/submit")
    public ModelAndView saveRole(@Valid Role role, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("role/role-form");
        } else {
            roleRepository.save(role);
            redirectAttributes.addFlashAttribute("message", "The role has been saved successfully.");

            return new ModelAndView("redirect:/roles");
        }
    }

    @GetMapping("/roles/edit/{id}")
    public String editRole(@PathVariable("id") Integer id, Model model) {
        Role role = roleRepository.findById(id).get();
        List<Role> listRoles = roleRepository.findAll();
        model.addAttribute("role", role);
        model.addAttribute("listRoles", listRoles);
        model.addAttribute("pageTitle", "Edit role (ID: " + id + ")");
        return "role/role-form";
    }

    @GetMapping("/roles/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Integer id, Model model) {
        roleRepository.deleteById(id);
        return new ModelAndView("redirect:/roles");
    }
}
