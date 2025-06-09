package com.team4.backend.dto.response;

import com.team4.backend.entities.Agency;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record AgencyOfficeResponseDto(
        Integer id, @Size(max = 100) String officeMail,
        @Size(max = 50) String officeContactPersonName,
        @Size(max = 10) String officeContactNumber, AgencyResponseDto agency,
        AddressResponseDto officeAddress
) implements Serializable {
}
