package com.sirma.pms.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Using lombok to generate getter setter and constructors
 * Entity class for PROJECT TABLE with below columns
 * ID,NAME,DESCRIPTION,START_DATE,END_DATE
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private String description;
    @Column(name = "STARTDATE")
    private Date startDate;
    @Column(name = "ENDDATE")
    private Date endDate;
    private String status;
    @Column(name = "CREATEDBY")
    private String createdBy;
    @Column(name = "UPDATEDBY")
    private String updatedBy;

}
