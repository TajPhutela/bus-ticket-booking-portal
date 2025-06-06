package com.team4.backend.repository;

import com.team4.backend.entities.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface routeRepository extends JpaRepository<Route,Integer> {
    List<Route> findByFromCity(String fromCity);

    List<Route> findByToCity(String toCity);

    List<Route> findByFromCityAndToCity(String fromCity, String toCity);
}
