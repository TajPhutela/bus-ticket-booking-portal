package com.team4.backend.repository;

import com.team4.backend.entities.Agency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AgencyRepository extends JpaRepository<Agency, Integer> {
    List<Agency> findByEmail(String email);

}
