package com.company.timecompany.controllers;

import com.company.timecompany.entities.Customer;
import com.company.timecompany.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;
    final List<Customer> listCustomers = customerRepository.findAll();

    @GetMapping("/customers")
    public String listAllCustomers(Model model) {
        model.addAttribute("listCustomers", listCustomers);
        return "/customer/customers";
    }

    @GetMapping("/customers/new")
    public String createNewProduct(Customer customer, Model model) {
        model.addAttribute("customer", customer);
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("pageTitle", "Create New Customer");
        return "customer/customer-form";
    }

    @PostMapping("/customers/submit")
    public ModelAndView saveProduct(@Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("customer/customer-form");
        } else {
            customerRepository.save(customer);
            return new ModelAndView("redirect:/customers");
        }
    }

    @GetMapping("/customers/edit/{id}")
    public String editProduct(@PathVariable("id") Integer id, Model model) {
        Optional<Customer> customer = customerRepository.findById(id);
        model.addAttribute("customer", customer);
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");
        return "customer/customer-form";
    }

    @GetMapping("/customers/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") Integer id, Model model) {
        customerRepository.deleteById(id);
        return new ModelAndView("redirect:/customers");
    }

}