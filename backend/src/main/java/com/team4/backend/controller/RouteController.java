package com.team4.backend.controller;

import com.team4.backend.entities.Route;
import com.team4.backend.repository.routeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    @Autowired
    private routeRepository routeRepository;

    @GetMapping("")
    public ResponseEntity<List<Route>> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }

    @GetMapping("/{route_id}")
    public ResponseEntity<Route> getRouteById(@PathVariable("route_id") int route_id) {
        Optional<Route> route = routeRepository.findById(route_id);
        if (route.isPresent()) {
            return new ResponseEntity<>(route.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
