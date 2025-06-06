package com.team4.backend.controller;

import com.team4.backend.dto.RouteDto;
import com.team4.backend.entities.Route;
import com.team4.backend.mapper.RouteMapper;
import com.team4.backend.repository.routeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    @Autowired
    private routeRepository routeRepository;

    @Autowired
    private RouteMapper routeMapper;


    @PostMapping("")
    public ResponseEntity<RouteDto> addRoute(@RequestBody RouteDto routeDto) {
        Route route = routeMapper.toEntity(routeDto);
        Route savedRoute = routeRepository.save(route);
        return new ResponseEntity<>(routeMapper.toDto(savedRoute), HttpStatus.CREATED);
    }


    @GetMapping("")
    public ResponseEntity<List<RouteDto>> getAllRoutes() {
        List<Route> routes = routeRepository.findAll();
        List<RouteDto> routeDtos = routes.stream()
                .map(routeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(routeDtos, HttpStatus.OK);
    }



    @GetMapping("/{route_id}")
    public ResponseEntity<RouteDto> getRouteById(@PathVariable("route_id") int routeId) {
        return routeRepository.findById(routeId)
                .map(route -> new ResponseEntity<>(routeMapper.toDto(route), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @GetMapping("/fromcity/{from_city}")
    public ResponseEntity<List<RouteDto>> getRoutesByFromCity(@PathVariable("from_city") String fromCity) {
        List<Route> routes = routeRepository.findByFromCity(fromCity);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<RouteDto> routeDtos = routes.stream()
                .map(routeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(routeDtos, HttpStatus.OK);
    }



    @GetMapping("/tocity/{to_city}")
    public ResponseEntity<List<RouteDto>> getRoutesByToCity(@PathVariable("to_city") String toCity) {
        List<Route> routes = routeRepository.findByToCity(toCity);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<RouteDto> routeDtos = routes.stream()
                .map(routeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(routeDtos, HttpStatus.OK);
    }



    @GetMapping("/{from_city}/{to_city}")
    public ResponseEntity<List<RouteDto>> getRoutesByFromAndTo(@PathVariable("from_city") String fromCity,
                                                               @PathVariable("to_city") String toCity) {
        List<Route> routes = routeRepository.findByFromCityAndToCity(fromCity, toCity);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<RouteDto> routeDtos = routes.stream()
                .map(routeMapper::toDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(routeDtos, HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity<RouteDto> updateRoute(@PathVariable("id") Integer id,
                                                @RequestBody RouteDto routeDto) {
        Optional<Route> optionalRoute = routeRepository.findById(id);

        if (optionalRoute.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Route existingRoute = optionalRoute.get();
        Route updated = routeMapper.partialUpdate(routeDto, existingRoute);
        Route saved = routeRepository.save(updated);

        return new ResponseEntity<>(routeMapper.toDto(saved), HttpStatus.OK);
    }
}
