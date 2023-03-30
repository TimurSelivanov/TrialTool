package com.trialtool.controller;

import com.trialtool.model.Customer;
import com.trialtool.model.Tool;
import com.trialtool.service.CustomersService;
import com.trialtool.service.ToolsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/tools")
public class ToolsController {

    private final ToolsService toolsService;
    private final CustomersService customersService;

    @Autowired
    public ToolsController(ToolsService toolsService, CustomersService customersService) {
        this.toolsService = toolsService;
        this.customersService = customersService;
    }

    @GetMapping()
    public String getAll(Model model) {
        model.addAttribute("tools", toolsService.findAll());
        return "tools/tools";
    }

    @GetMapping("/{id}")
    public String getById(@PathVariable("id") int id, Model model, @ModelAttribute("customer") Customer customer) {
        model.addAttribute("tool", toolsService.findOne(id));

        Customer toolHolder = toolsService.getToolHolder(id);

        if (toolHolder != null) {
            model.addAttribute("holder", toolHolder);
        } else {
            model.addAttribute("customers", toolsService.findAll());
        }
        return "tools/details";
    }

    @GetMapping("/new")
    public String newTool(@ModelAttribute("tool") Tool tool) {
        return "tools/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("tool") @Valid Tool tool, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "tools/new";
        }
        toolsService.save(tool);
        return "redirect:/tools";
    }

    @GetMapping("/{id}/update")
    public String showUpdate(Model model, @PathVariable("id") int id) {
        model.addAttribute("tool", toolsService.findOne(id));
        return "tools/update";
    }

    @PatchMapping("/{id}")
    public String doUpdate(@ModelAttribute("tool") @Valid Tool tool, BindingResult bindingResult,
                           @PathVariable("id") int id) {
        if(bindingResult.hasErrors()) {
            return "tools/update";
        }
        toolsService.update(id, tool);
        return "redirect:/tools";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        toolsService.delete(id);
        return "redirect:/tools";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        toolsService.release(id);
        return "redirect:/tools/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("customer") Customer selectedCustomer) {
        //selectedCustomer has only one id field, the other fields is null
        toolsService.assign(id, selectedCustomer);
        return "redirect:/tools/" + id;
    }

    @GetMapping("/search")
    public String searchPage() {
        return "tools/search";
    }

    @PostMapping("/search")
    public String doSearch(Model model, @RequestParam("brand") String brand) {
        model.addAttribute("tools", toolsService.searchByBrand(brand));
        return "tools/search";
    }

}
