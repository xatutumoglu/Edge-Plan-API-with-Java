package com.edgeplan.edgeplan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetCreateRequest {

    @NotBlank
    private String title;

    private String description;

    @NotNull
    private LocalDate endDate;
}