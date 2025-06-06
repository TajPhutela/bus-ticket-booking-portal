package com.team4.backend.repository;

import com.team4.backend.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface routeRepository extends JpaRepository<Route,Integer> {
}
