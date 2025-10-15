package com.edgeplan.edgeplan.dto;

import com.edgeplan.edgeplan.model.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TargetResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate endDate;
    private Status status;
    private String createdBy;
    private String changedBy;
    private LocalDateTime createdAt;
    private LocalDateTime changedAt;
}