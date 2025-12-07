package com.mvc.mvc.controllers;

import com.mvc.mvc.dto.EmployeeDTO;
import com.mvc.mvc.entities.EmployeeEntity;
import com.mvc.mvc.repositories.EmployeeRepository;
import com.mvc.mvc.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

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
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeId) {
//        return new EmployeeDTO(employeeId, "Akshit", "akshit07@gmail.com", 28, true, LocalDate.of(2024, 1, 2));
        Optional<EmployeeDTO> employee =  employeeService.getEmployeeById(employeeId);
//        if (employee == null) {
//            return ResponseEntity.notFound().build();
//        }
//        return ResponseEntity.ok(employee);
        return employee
                .map(employeeDTO -> ResponseEntity.ok(employeeDTO))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age,
                                                @RequestParam(required = false) String sortBy) {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO inputEmployee) {
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@RequestBody EmployeeDTO employeeDTO, @PathVariable Long employeeId) {
//        return employeeService.updateEmployeeById(employeeId, employeeDTO);
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeId, employeeDTO));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Boolean> deleteEmployeeById(@PathVariable(name = "id") Long employeeId) {
//        return ResponseEntity.ok(employeeService.deleteEmployeeById(employeeId));
        boolean gotDeleted = employeeService.deleteEmployeeById(employeeId);
        if (gotDeleted) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployee(@RequestBody Map<String, Object> updates,
                                             @PathVariable Long employeeId) {
        EmployeeDTO employeeDTO = employeeService.updatePartialEmployeeById(employeeId, updates);
        if (employeeDTO == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeDTO);
    }
}
