package com.mvc.mvc.services;

import com.mvc.mvc.dto.DepartmentDto;
import com.mvc.mvc.entities.DepartmentEntity;
import com.mvc.mvc.entities.EmployeeEntity;
import com.mvc.mvc.exceptions.ResourceNotFoundException;
import com.mvc.mvc.repositories.DepartmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final ModelMapper modelMapper;

    public DepartmentService(DepartmentRepository departmentRepository, ModelMapper modelMapper) {
        this.departmentRepository = departmentRepository;
        this.modelMapper = modelMapper;
    }

    public Optional<DepartmentDto> getDepartmentById(Long id) {
        return departmentRepository.findById(id).map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDto.class));
    }

    public List<DepartmentDto> getAllDepartments() {
        List<DepartmentEntity> departmentEntities = departmentRepository.findAll();

        return departmentEntities
                .stream()
                .map(departmentEntity -> modelMapper.map(departmentEntity, DepartmentDto.class))
                .collect(Collectors.toList());
    }

    public DepartmentDto createDepartment(DepartmentDto inputDepartment) {
        DepartmentEntity departmentEntity = modelMapper.map(inputDepartment, DepartmentEntity.class);
        DepartmentEntity savedDepartment = departmentRepository.save(departmentEntity);

        return modelMapper.map(savedDepartment, DepartmentDto.class);
    }

    public void isExistsDepartmentById(Long departmentId) {
        boolean exists = departmentRepository.existsById(departmentId);

        if (!exists)
            throw new ResourceNotFoundException("Department not found with id:"+departmentId);
    }

    public DepartmentDto updateDepartment(Long departmentId, DepartmentDto departmentDto) {
        isExistsDepartmentById(departmentId);
        DepartmentEntity departmentEntity = modelMapper.map(departmentDto, DepartmentEntity.class);
        departmentEntity.setId(departmentId);
        DepartmentEntity updatedDepartmentEntity = departmentRepository.save(departmentEntity);
        return modelMapper.map(updatedDepartmentEntity, DepartmentDto.class);
    }

    public DepartmentDto partialUpdateDepartment(Long departmentId, Map<String, Object> updates) {
        isExistsDepartmentById(departmentId);

        DepartmentEntity departmentEntity = departmentRepository.findById(departmentId).get();

        updates.forEach((field, value) -> {
            Field fieldToBeUpdated = ReflectionUtils.findField(DepartmentEntity.class, field);
            if (fieldToBeUpdated != null) {
                fieldToBeUpdated.setAccessible(true);
                ReflectionUtils.setField(fieldToBeUpdated, departmentEntity, value);
            }
        });

        return modelMapper.map(departmentRepository.save(departmentEntity), DepartmentDto.class);
    }
}
