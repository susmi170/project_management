package com.sirma.pms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Using lombok to generate getter setter and constructors
 * DTO class corresponding to Project entity class
 * Any data passing to and from controller will use this DTO.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    @NotNull(message = "Id is mandatory")

    private int id;
    @NotBlank(message = "Name is mandatory")

    private String name;
    @NotBlank(message = "Description is mandatory")

    private String description;
    @NotNull(message = "Start Date is mandatory")

    private Date startDate;
    @NotNull(message = "End Date is mandatory")

    private Date endDate;
    private String status;
    private String createdBy;
    private String updatedBy;

    public ProjectDto(String name, String description, Date startDate, Date endDate, String status, String createdBy, String updatedBy) {
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
