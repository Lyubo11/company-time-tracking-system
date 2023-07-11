package com.company.timecompany.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.company.timecompany.entities.Customer;
import com.company.timecompany.repositories.CustomerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Incubating;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {
    @Mock
    private CustomerRepository customerRepository;
    @InjectMocks
    private CustomerController customerController;

    @Test
    void testShouldFetchAllCustomers() {
        List<Customer> customers = List.of(Customer.builder().id(1).name("ivan").build(),
                                           Customer.builder().id(2).name("dragan").build());

        when(customerRepository.findAll()).thenReturn(customers);
        Model model = mock(Model.class);
        String expected = customerController.listAllCustomers(model);

        assertEquals("/customer/customers",expected);
    }

    @Test
    void testShouldCreateNewProduct() {
        Customer customer =Customer.builder().id(1).name("ivan").build();
        List<Customer> customers = List.of(customer);
        Model model = mock(Model.class);

         when(customerRepository.findAll()).thenReturn(customers);
        String expected = customerController.createNewProduct(customer,model);

        assertEquals("customer/customer-form",expected);
    }

    @Test
    void testShouldSaveNewCustomerWhenHasNoErrors() {
        Customer customer = Customer.builder().id(1).name("ivan").build();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        ModelAndView expected = customerController.saveProduct(customer,bindingResult);

        assertEquals("redirect:/customers",expected.getViewName());

    }
    @Test
    void testShouldSaveNewCustomerWhenHasError() {
        Customer customer = Customer.builder().id(1).name("ivan").build();
        BindingResult bindingResult = mock(BindingResult.class);

        when(bindingResult.hasErrors()).thenReturn(true);

        ModelAndView expected = customerController.saveProduct(customer,bindingResult);

        assertEquals("customer/customer-form",expected.getViewName());
    }

    @Test
    void testShouldEditCustomer() {
        int id = 1;
        Optional<Customer> customer = Optional.ofNullable((Customer.builder().id(id).build()));
        List<Customer> customers = List.of(Customer.builder().id(2).build());
        Model model = mock(Model.class);

        when(customerRepository.findById(id)).thenReturn(customer);
        when(customerRepository.findAll()).thenReturn(customers);

        String expected = customerController.editProduct(id,model);

        assertEquals("customer/customer-form",expected);

    }
}