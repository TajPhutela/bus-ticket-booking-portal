package com.team4.backend.dto;

import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;


public record AgencyOfficeDto(Integer id, @Size(max = 100) String officeMail,
                              @Size(max = 50) String officeContactPersonName,
                              @Size(max = 10) String officeContactNumber, Integer agencyId,
                              Integer officeAddressId) implements Serializable {
}