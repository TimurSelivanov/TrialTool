package com.trialtool.service;

import com.trialtool.model.Customer;
import com.trialtool.model.Tool;
import com.trialtool.repository.ToolsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ToolsService {

    private final ToolsRepository toolsRepository;

    @Autowired
    public ToolsService(ToolsRepository toolsRepository) {
        this.toolsRepository = toolsRepository;
    }

    public List<Tool> findAll() {
        return toolsRepository.findAll();
    }

    public Tool findOne(int id) {
        Optional<Tool> foundTool = toolsRepository.findById(id);
        return foundTool.orElse(null);
    }

    public List<Tool> searchByBrand(String brand) {
        return toolsRepository.findByBrandStartingWith(brand);
    }

    @Transactional
    public void save(Tool tool) {
        toolsRepository.save(tool);
    }

    @Transactional
    public void update(int id, Tool updatedTool) {
        Tool toolToUpdate = toolsRepository.findById(id).get();

        updatedTool.setId(id);
        updatedTool.setCustomer(toolToUpdate.getCustomer());
        toolsRepository.save(updatedTool);
    }

    @Transactional
    public void delete(int id) {
        toolsRepository.deleteById(id);
    }

    //return null if tool has no customer
    public Customer getToolCustomer(int id) {
        //no need to call Hibernate.initialize. Customer is "One" side and has eager loading rule
        return toolsRepository.findById(id).map(Tool::getCustomer).orElse(null);
    }

    //this method release tool from customer
    @Transactional
    public void release(int id) {
        toolsRepository.findById(id).ifPresent(tool -> {
            tool.setCustomer(null);
            tool.setTakenOn(null);
        });
    }

    //this method assigns tool with customer
    @Transactional
    public void assign(int id, Customer customer) {
        toolsRepository.findById(id).ifPresent(tool -> {
            tool.setCustomer(customer);
            tool.setTakenOn(new Date()); //set current date
        });
    }

}
