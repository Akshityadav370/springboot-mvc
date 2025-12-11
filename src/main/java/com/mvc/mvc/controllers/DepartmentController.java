package com.mvc.mvc.controllers;

import com.mvc.mvc.dto.DepartmentDto;
import com.mvc.mvc.exceptions.ResourceNotFoundException;
import com.mvc.mvc.services.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<DepartmentDto>> getAllDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(name = "id") Long departmentId) {
        Optional<DepartmentDto> departmentDtoOptional = departmentService.getDepartmentById(departmentId);
        return departmentDtoOptional
                .map(departmentDto -> ResponseEntity.ok(departmentDto))
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id"+departmentId));
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createNewDepartment(@RequestBody DepartmentDto inputDepartment) {
        DepartmentDto departmentDto = departmentService.createDepartment(inputDepartment);
//        return ResponseEntity.ok(departmentDto);
        return new ResponseEntity<>(departmentDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<DepartmentDto> updateDepartmentById(@PathVariable(name = "id") Long departmentId,@RequestBody DepartmentDto inputDepartment) {
        return ResponseEntity.ok(departmentService.updateDepartment(departmentId, inputDepartment));
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<DepartmentDto> updateDepartmentByIdPartially(@PathVariable(name = "id") Long departmentId, @RequestBody Map<String, Object> updates) {
        DepartmentDto departmentDto = departmentService.partialUpdateDepartment(departmentId, updates);

        if (departmentDto == null)  return ResponseEntity.notFound().build();
        return ResponseEntity.ok(departmentDto);
    }
}
