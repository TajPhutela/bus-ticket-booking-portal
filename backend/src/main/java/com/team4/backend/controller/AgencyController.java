package com.team4.backend.controller;

import com.team4.backend.dto.request.AgencyOfficeRequestDto;
import com.team4.backend.dto.request.AgencyRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Agency;
import com.team4.backend.entities.AgencyOffice;
import com.team4.backend.mapper.AgencyMapper;
import com.team4.backend.mapper.AgencyOfficeMapper;
import com.team4.backend.repository.AgencyOfficeRepository;
import com.team4.backend.repository.AgencyRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/agencies")
public class AgencyController {
    @Autowired
    private AgencyRepository agencyRepository;
    @Autowired
    private AgencyOfficeRepository agencyOfficeRepository;
    @Autowired
    private AgencyMapper agencyMapper;
    @Autowired
    private AgencyOfficeMapper agencyOfficeMapper;

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<AgencyRequestDto>>> getAllAgencies() {
        List<Agency> agencies = agencyRepository.findAll();
        if (agencies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No agencies found"));
        }
        List<AgencyRequestDto> agencyRequestDtos = agencies.stream().map(agencyMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(agencyRequestDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AgencyRequestDto>> getAgencyById(@PathVariable("id") Integer agencyId) {
        Optional<Agency> agency = agencyRepository.findById(agencyId);
        if (agency.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Agency not found with id " + agencyId));
        }
        return ResponseEntity.ok(ApiResponse.success(agencyMapper.toDto(agency.get())));
    }

    @GetMapping("/offices")
    public ResponseEntity<ApiResponse<List<AgencyOfficeRequestDto>>> getOffices() {
        List<AgencyOffice> offices = agencyOfficeRepository.findAll();
        if (offices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No agency offices found"));
        }
        List<AgencyOfficeRequestDto> dtoList = offices.stream().map(agencyOfficeMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @GetMapping("/offices/agency_id/{agency}")
    public ResponseEntity<ApiResponse<List<AgencyOfficeRequestDto>>> getOfficesByAgencyId(@PathVariable("agency") Integer agencyId) {
        List<AgencyOffice> offices = agencyOfficeRepository.findByAgencyId(agencyId);
        if (offices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No offices found for agency ID " + agencyId));
        }
        List<AgencyOfficeRequestDto> dtoList = offices.stream().map(agencyOfficeMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @GetMapping("/offices/{id}")
    public ResponseEntity<ApiResponse<AgencyOfficeRequestDto>> getOfficeById(@PathVariable("id") Integer officeId) {
        Optional<AgencyOffice> officeOpt = agencyOfficeRepository.findById(officeId);
        if (officeOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Office not found with id " + officeId));
        }
        return ResponseEntity.ok(ApiResponse.success(agencyOfficeMapper.toDto(officeOpt.get())));
    }

    @GetMapping("/offices/email/{email}")
    public ResponseEntity<ApiResponse<List<AgencyOfficeRequestDto>>> getOfficeEmails(@PathVariable("email") String emailId) {
        List<AgencyOffice> offices = agencyOfficeRepository.findByOfficeMail(emailId);
        if (offices.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No offices found with email " + emailId));
        }
        List<AgencyOfficeRequestDto> dtoList = offices.stream().map(agencyOfficeMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<List<AgencyRequestDto>>> getEmails(@PathVariable("email") String email) {
        List<Agency> agencies = agencyRepository.findByEmail(email);
        if (agencies.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), "No agencies found with email " + email));
        }
        List<AgencyRequestDto> dtoList = agencies.stream().map(agencyMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtoList));
    }


    @PostMapping("")
    public ResponseEntity<String> createAgency(@RequestBody @Valid AgencyRequestDto agencyRequestDto) {
        Agency agency = agencyMapper.toEntity(agencyRequestDto);
        agencyRepository.save(agency);
        return ResponseEntity.status(HttpStatus.CREATED).body("Record Created Successfully");
    }

    @PutMapping("/{agency_id}")
    public ResponseEntity<ApiResponse<AgencyRequestDto>> updateAgency(@PathVariable("agency_id") Integer agencyId,
                                                                      @RequestBody @Valid AgencyRequestDto agencyRequestDto) {
        return agencyRepository.findById(agencyId)
                .map(existingAgency -> {
                    Agency updatedAgency = agencyMapper.toEntity(agencyRequestDto);
                    updatedAgency.setId(agencyId);
                    Agency savedAgency = agencyRepository.save(updatedAgency);
                    return ResponseEntity.ok(
                            ApiResponse.success(HttpStatus.OK.value(), "Agency updated successfully", agencyMapper.toDto(savedAgency))
                    );
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Agency not found")
                ));
    }


    @PostMapping("/office")
    public ResponseEntity<String> addAgencyOffice(@Valid @RequestBody AgencyOfficeRequestDto requestDto) {
        AgencyOffice agencyOffice = agencyOfficeMapper.toEntity(requestDto);
        agencyOfficeRepository.save(agencyOffice);

        return ResponseEntity.status(HttpStatus.CREATED).body("Record Created Successfully.");
    }

    @PutMapping("/offices/{office_id}")
    public ResponseEntity<ApiResponse<AgencyOfficeRequestDto>> updateAgencyOffice(
            @PathVariable("office_id") Integer officeId,
            @RequestBody @Valid AgencyOfficeRequestDto officeDto) {

        return agencyOfficeRepository.findById(officeId)
                .map(existingOffice -> {
                    AgencyOffice updatedOffice = agencyOfficeMapper.toEntity(officeDto);
                    updatedOffice.setId(officeId);
                    AgencyOffice savedOffice = agencyOfficeRepository.save(updatedOffice);
                    return ResponseEntity.ok(
                            ApiResponse.success(HttpStatus.OK.value(), "Office updated successfully", agencyOfficeMapper.toDto(savedOffice))
                    );
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        ApiResponse.error(HttpStatus.NOT_FOUND.value(), "Office not found")
                ));
    }

}
