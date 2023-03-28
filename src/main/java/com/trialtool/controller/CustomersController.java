package com.trialtool.controller;

import com.trialtool.model.Customer;
import com.trialtool.service.CustomersService;
import com.trialtool.util.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers")
public class CustomersController {

    private final CustomersService customersService;
    private final CustomerValidator customerValidator;

    @Autowired
    public CustomersController(CustomersService customersService, CustomerValidator customerValidator) {
        this.customersService = customersService;
        this.customerValidator = customerValidator;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("customers", customersService.findAll());
        return "customers/customers";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model) {
        model.addAttribute("customer", customersService.findOne(id));
        model.addAttribute("tools", customersService.getToolsByCustomerId(id));
        return "customers/details";
    }

    @GetMapping("/new")
    public String newCustomer(@ModelAttribute("customer") Customer customer) {
        return "customers/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult) {
        customerValidator.validate(customer, bindingResult);

        if(bindingResult.hasErrors()) {
            return "customers/new";
        }
        customersService.save(customer);
        return "customers/customers";
    }

    @GetMapping("/{id}/update")
    public String showUpdate(Model model, @PathVariable("id") int id) {
        model.addAttribute("customer", customersService.findOne(id));
        return "customers/update";
    }

    @PatchMapping("/{id}")
    public String doUpdate(@ModelAttribute("customer") @Valid Customer customer, BindingResult bindingResult,
                           @PathVariable("id") int id) {
        if(bindingResult.hasErrors()){
            return "customers/update";
        }
        customersService.updateCustomer(id, customer);
        return "customers/customers";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        customersService.delete(id);
        return "customers/customers";
    }
}
