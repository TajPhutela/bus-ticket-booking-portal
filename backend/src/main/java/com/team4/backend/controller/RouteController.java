package com.team4.backend.controller;

import com.team4.backend.dto.RouteDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.entities.Route;
import com.team4.backend.mapper.RouteMapper;
import com.team4.backend.repository.routeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    private final routeRepository routeRepository;
    private final RouteMapper routeMapper;

    public RouteController(routeRepository routeRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<RouteDto>> addRoute(@RequestBody RouteDto routeDto) {
        Route savedRoute = routeRepository.save(routeMapper.toEntity(routeDto));
        return new ResponseEntity<>(
                ApiResponse.success(HttpStatus.CREATED.value(), "Route created", routeMapper.toDto(savedRoute)),
                HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<RouteDto>>> getAllRoutes() {
        List<RouteDto> dtos = routeRepository.findAll()
                .stream().map(routeMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{route_id}")
    public ResponseEntity<ApiResponse<RouteDto>> getRouteById(@PathVariable("route_id") int routeId) {
        return routeRepository.findById(routeId)
                .map(route -> ResponseEntity.ok(ApiResponse.success(routeMapper.toDto(route))))
                .orElseGet(() -> new ResponseEntity<>(ApiResponse.error(404, "Route not found"), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fromcity/{from_city}")
    public ResponseEntity<ApiResponse<List<RouteDto>>> getRoutesByFromCity(@PathVariable String from_city) {
        List<Route> routes = routeRepository.findByFromCity(from_city);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes from " + from_city), HttpStatus.NOT_FOUND);
        }
        List<RouteDto> dtos = routes.stream().map(routeMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/tocity/{to_city}")
    public ResponseEntity<ApiResponse<List<RouteDto>>> getRoutesByToCity(@PathVariable String to_city) {
        List<Route> routes = routeRepository.findByToCity(to_city);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes to " + to_city), HttpStatus.NOT_FOUND);
        }
        List<RouteDto> dtos = routes.stream().map(routeMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{from_city}/{to_city}")
    public ResponseEntity<ApiResponse<List<RouteDto>>> getRoutesByFromAndTo(
            @PathVariable String from_city,
            @PathVariable String to_city) {
        List<Route> routes = routeRepository.findByFromCityAndToCity(from_city, to_city);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes found from " + from_city + " to " + to_city), HttpStatus.NOT_FOUND);
        }
        List<RouteDto> dtos = routes.stream().map(routeMapper::toDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RouteDto>> updateRoute(@PathVariable Integer id, @RequestBody RouteDto routeDto) {
        return routeRepository.findById(id)
                .map(existingRoute -> {
                    Route updated = routeMapper.partialUpdate(routeDto, existingRoute);
                    Route saved = routeRepository.save(updated);
                    return ResponseEntity.ok(ApiResponse.success(routeMapper.toDto(saved)));
                })
                .orElseGet(() -> new ResponseEntity<>(ApiResponse.error(404, "Route not found"), HttpStatus.NOT_FOUND));
    }
}
