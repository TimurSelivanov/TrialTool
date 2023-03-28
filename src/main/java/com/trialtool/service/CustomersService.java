package com.trialtool.service;

import com.trialtool.model.Customer;
import com.trialtool.model.Tool;
import com.trialtool.repository.CustomersRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CustomersService {

    private final CustomersRepository customersRepository;

    @Autowired
    public CustomersService(CustomersRepository customersRepository) {
        this.customersRepository = customersRepository;
    }

    public List<Customer> findAll() {
        return customersRepository.findAll();
    }

    public Customer findOne(int id) {
        return customersRepository.getOne(id);
    }

    @Transactional
    public void save(Customer customer) {
        customersRepository.save(customer);
    }

    @Transactional
    public void updateCustomer(int id, Customer updatedCustomer) {
        updatedCustomer.setId(id);
        customersRepository.save(updatedCustomer);
    }

    @Transactional
    public void delete(int id) {
        customersRepository.deleteById(id);
    }

    public Optional<Customer> findCustomerByPhone(int phone) {
        return customersRepository.findByPhone(phone);
    }

    public List<Tool> getToolsByCustomerId(int id) {
        Optional<Customer> customer = customersRepository.findById(id);

        if (customer.isPresent()) {
            Hibernate.initialize(customer.get().getTools());
            //load customers books from database

            //checking if trial period was expired
            customer.get().getTools().forEach(tool -> {
                long diffInMillis = Math.abs(tool.getTakenOn().getTime() - new Date().getTime());

                if (diffInMillis > 604800000) {
                    tool.setExpired(true); //expired
                }
            });
            return customer.get().getTools();
        } else {
            return Collections.emptyList();
        }
    }


}
