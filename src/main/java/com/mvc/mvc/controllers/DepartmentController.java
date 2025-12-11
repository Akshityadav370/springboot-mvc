package com.mvc.mvc.controllers;

import com.mvc.mvc.dto.DepartmentDto;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {
    @GetMapping(path = "/")
    public String getAllDepartments() {
        return "Hi";
    }

    @GetMapping(path = "/{id}")
    public DepartmentDto getDepartmentById(@PathVariable(name = "id") Long departmentId) {
        return new DepartmentDto(departmentId, "New Department", true, LocalDate.now());
    }

    @PostMapping
    public DepartmentDto createNewDepartment(@RequestBody DepartmentDto inputDepartment) {
        inputDepartment.setId(123L);
        return inputDepartment;
    }

    @PutMapping(path = "/{id}")
    public String updateDepartmentById(@RequestBody DepartmentDto inputDepartment) {
        return "Hello from put";
    }
}
