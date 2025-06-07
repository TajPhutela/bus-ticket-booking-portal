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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @GetMapping("/{id}")
    public AgencyDto getAgencyById(@PathVariable("id") Integer agencyId) {
        Optional<Agency> agency = agencyRepository.findById(agencyId);
        return agency.map(agencyMapper::toDto).orElse(null);
    }

    @GetMapping("/offices/{id}")
    public List<AgencyOfficeDto> getOfficesByAgencyId(@PathVariable("id") Integer agencyId) {
        List<AgencyOffice> offices = agencyOfficeRepository.findByAgencyId(agencyId);
        return offices.stream()
                .map(agencyOfficeMapper::toDto)
                .collect(Collectors.toList());
    }
}
