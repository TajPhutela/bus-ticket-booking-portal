package com.team4.backend.controller;

import com.team4.backend.dto.AgencyDto;
import com.team4.backend.dto.AgencyOfficeDto;
import com.team4.backend.entities.Agency;
import com.team4.backend.entities.AgencyOffice;
import com.team4.backend.mapper.AgencyMapper;
import com.team4.backend.mapper.AgencyOfficeMapper;
import com.team4.backend.repository.AgencyOfficeRepository;
import com.team4.backend.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<AgencyDto> getAllAgencies() {
        List<Agency> agencies = agencyRepository.findAll();
        return agencies.stream()
                .map(agencyMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public AgencyDto getAgencyById(@PathVariable("id") Integer agencyId) {
        Optional<Agency> agency = agencyRepository.findById(agencyId);
        return agency.map(agencyMapper::toDto).orElse(null);
    }

    @GetMapping("/offices/agency_id/{agency}")
    public List<AgencyOfficeDto> getOfficesByAgencyId(@PathVariable("agency") Integer agencyId) {
        List<AgencyOffice> offices = agencyOfficeRepository.findByAgencyId(agencyId);
        return offices.stream()
                .map(agencyOfficeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/offices/{id}")
    public AgencyOfficeDto getOfficeById(@PathVariable("id") Integer officeId) {
        AgencyOffice office = agencyOfficeRepository.findById(officeId).get();
        return agencyOfficeMapper.toDto(office);
    }

    @GetMapping("/offices")
    public List<AgencyOfficeDto> getOffices() {
        List<AgencyOffice> offices = agencyOfficeRepository.findAll();
        return offices.stream()
                .map(agencyOfficeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/offices/email/{email}")
    public List<AgencyOfficeDto> getOfficeEmails(@PathVariable("email") String emailId) {
        List<AgencyOffice> offices = agencyOfficeRepository.findByOfficeMail(emailId);
        return offices.stream()
                .map(agencyOfficeMapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/email/{email}")
    public List<AgencyDto> getEmails(@PathVariable("email") String email) {
        List<Agency> agencies = agencyRepository.findByEmail(email);
        return agencies.stream()
                .map(agencyMapper::toDto)
                .collect(Collectors.toList());
    }
}
