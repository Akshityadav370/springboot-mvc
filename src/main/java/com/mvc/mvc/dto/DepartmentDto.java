package com.mvc.mvc.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentDto {
    private Long id;

    @NotBlank(message = "Name of the title cannot be blank")
    @Size(min = 3, max = 10, message = "Number of characters in title should be in the range: [3, 10]")
    private String title;

    private Boolean isActive;

    private LocalDate createdAt;
}
