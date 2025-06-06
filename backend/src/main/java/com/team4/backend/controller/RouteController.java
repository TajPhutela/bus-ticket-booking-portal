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


    @PostMapping("")
    public ResponseEntity<?> addRoute(@RequestBody Route route) {
        routeRepository.save(route);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


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


    @GetMapping("/fromcity/{from_city}")
    public ResponseEntity<List<Route>> getRoutesByFromCity(@PathVariable("from_city") String from_city) {
        List<Route> routes = routeRepository.findByFromCity(from_city);
        if(routes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }


    @GetMapping("/tocity/{tocity}")
    public ResponseEntity<List<Route>> getRoutesByToCity(@PathVariable("tocity") String tocity) {
        List<Route> routes = routeRepository.findByToCity(tocity);
        if(routes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(routes, HttpStatus.OK);
    }


    @GetMapping("/{from_city}/{to_city}")
    public ResponseEntity<List<Route>> getDestination(@PathVariable("from_city") String from_city, @PathVariable("to_city") String toCity) {
        List<Route> routes = routeRepository.findByFromCityAndToCity(from_city, toCity);
        if(routes.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(routes, HttpStatus.OK);

    }


    public ResponseEntity<Route> updateRoute(@RequestBody Route route) {

        Optional<Route> routeOptional = routeRepository.findById(route.getId());

        if (routeOptional.isPresent()) {
            return new ResponseEntity<>(routeRepository.save(route), HttpStatus.OK);
        }

        Route existingRoute = routeRepository.findById(route.getId()).get();

        existingRoute.setFromCity(route.getFromCity());
        existingRoute.setToCity(route.getToCity());
        existingRoute.setBreakPoints(route.getBreakPoints());
        existingRoute.setDuration(route.getDuration());

        Route updatedRoute = routeRepository.save(existingRoute);

        return new ResponseEntity<>(updatedRoute, HttpStatus.OK);
    }
}
