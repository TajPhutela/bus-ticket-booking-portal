package com.team4.backend.repository;

import com.team4.backend.entities.AgencyOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgencyOfficeRepository extends JpaRepository<AgencyOffice, Long> {
    List<AgencyOffice> findByAgencyId(Integer agencyId);
}
