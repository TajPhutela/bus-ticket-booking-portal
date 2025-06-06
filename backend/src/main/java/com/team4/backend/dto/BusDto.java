package com.team4.backend.dto;

import lombok.Data;

@Data
public class BusDto {
    private Integer id;
    private Integer officeId;
    private String registrationNumber;
    private Integer capacity;
    private String type;
}

