package com.openclassrooms.webapp.controller;

import com.openclassrooms.webapp.model.Employee;
import com.openclassrooms.webapp.repository.EmployeeProxy;
import com.openclassrooms.webapp.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {

    EmployeeService service;

    @GetMapping("/")
    public String home(Model model) {
        Iterable<Employee> listEmployee = service.getEmployees();
    model.addAttribute("employees", listEmployee);
        return "home";

    }

    @GetMapping("/deleteEmployee/{id}")
    public ModelAndView deleteEmployee(@PathVariable("id") final int id) {
        service.deleteEmployee(id);
        return new ModelAndView("redirect:/");
    }

    @PostMapping("/saveEmployee")
    public ModelAndView saveEmployee(@ModelAttribute Employee employee) {
        // @ModelAttribute permet à Spring de récupérer les données saisies dans les champs
        // du formulaire et de construire un objet Employee avec.
        service.saveEmployee(employee);
        return new ModelAndView("redirect:/");
    }
}
