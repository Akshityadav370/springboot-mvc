package com.mvc.mvc.controllers;

import com.mvc.mvc.dto.EmployeeDTO;
import com.mvc.mvc.entities.EmployeeEntity;
import com.mvc.mvc.repositories.EmployeeRepository;
import com.mvc.mvc.services.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    //    @GetMapping(path = "/getMessage")
//    public String getMySecret() {
//        return "Secret Message";
//    }
    @GetMapping("/{employeeId}")
    public EmployeeDTO getEmployeeById(@PathVariable Long employeeId) {
//        return new EmployeeDTO(employeeId, "Akshit", "akshit07@gmail.com", 28, true, LocalDate.of(2024, 1, 2));
        return employeeService.getEmployeeById(employeeId);
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee) {
        return employeeService.createNewEmployee(inputEmployee);
    }

    @PutMapping("/{id}")
    public EmployeeEntity updateEmployee(@RequestBody EmployeeEntity updateEmployee, @RequestParam(name = "id") Long id) {
        return employeeRepository.findById(id).orElse(null);
    }
}
