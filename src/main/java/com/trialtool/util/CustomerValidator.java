package com.trialtool.util;

import com.trialtool.model.Customer;
import com.trialtool.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CustomerValidator implements Validator {

    private final CustomersService customersService;

    @Autowired
    public CustomerValidator(CustomersService customersService) {
        this.customersService = customersService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (Customer) o;

        if (customersService.findCustomerByPhone(customer.getPhone()).isPresent())
            errors.rejectValue("phone", "", "Customer with this phone number is already exist");
    }
}
