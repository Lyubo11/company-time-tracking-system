package com.company.timecompany.controllers;

import com.company.timecompany.entities.Customer;
import com.company.timecompany.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public String listAllCustomers(Model model) {
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("listCustomers", listCustomers);
        return "customer/customers";
    }

    @GetMapping("/customers/new")
    public String newCustomer(Customer customer, Model model) {
        List<Customer> customerList = customerRepository.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("listRoles", customerList);
        return "/customer/customer-form";
    }

    @PostMapping("/customers/submit")
    public ModelAndView saveCustomer(@Valid Customer customer, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("/customers/new");
        } else {
            customerRepository.save(customer);
            redirectAttributes.addFlashAttribute("message", "The user has been saved successfully!");
            return new ModelAndView("redirect:/customers");
        }
    }
}
