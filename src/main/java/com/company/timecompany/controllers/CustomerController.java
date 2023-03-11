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
@Controller
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/customers")
    public String listAllCustomers(Model model) {
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("listCustomers", listCustomers);
        return "/customer/customers";
    }

    @GetMapping("/customers/new")
    private String createNewProduct(Customer customer, Model model) {
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("pageTitle", "Create New Customer");
        return "customer/customer-form";
    }

    @PostMapping("/customers/submit")
    private ModelAndView saveProduct(@Valid Customer customer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            System.out.println("error");
            return new ModelAndView("customer/customer-form");
        } else {
            System.out.println("save");
            customerRepository.save(customer);
            return new ModelAndView("redirect:/customers");
        }
    }

    @GetMapping("/customers/edit/{id}")
    private String editProduct(@PathVariable("id") Integer id, Model model) {
        Customer customer = customerRepository.findById(id).get();
        List<Customer> listCustomers = customerRepository.findAll();
        model.addAttribute("customer", customer);
        model.addAttribute("listCustomers", listCustomers);
        model.addAttribute("pageTitle", "Edit Customer (ID: " + id + ")");
        return "customer/customer-form";
    }

    @GetMapping("/customers/delete/{id}")
    private ModelAndView deleteProduct(@PathVariable("id") Integer id, Model model) {
        customerRepository.deleteById(id);
        return new ModelAndView("redirect:/customers");
    }

}
