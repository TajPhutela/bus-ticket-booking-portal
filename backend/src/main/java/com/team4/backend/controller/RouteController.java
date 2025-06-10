package com.team4.backend.controller;

import com.team4.backend.dto.request.RouteRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.dto.response.RouteResponseDto;
import com.team4.backend.entities.Route;
import com.team4.backend.mapper.RouteMapper;
import com.team4.backend.repository.RouteRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/routes")
public class RouteController {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    public RouteController(RouteRepository routeRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<RouteResponseDto>> addRoute(@RequestBody RouteRequestDto routeRequestDto) {
        if (routeRequestDto.id() != null && routeRepository.existsById(routeRequestDto.id())) {
            return new ResponseEntity<>(
                    ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "Route with ID " + routeRequestDto.id() + " already exists"),
                    HttpStatus.BAD_REQUEST
            );
        }

        Route savedRoute = routeRepository.save(routeMapper.toEntity(routeRequestDto));
        return new ResponseEntity<>(
                ApiResponse.success(HttpStatus.CREATED.value(), "Route created", routeMapper.toResponseDto(savedRoute)),
                HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<RouteResponseDto>>> getAllRoutes() {
        List<RouteResponseDto> dtos = routeRepository.findAll()
                .stream().map(routeMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{route_id}")
    public ResponseEntity<ApiResponse<RouteResponseDto>> getRouteById(@PathVariable("route_id") int routeId) {
        return routeRepository.findById(routeId)
                .map(route -> ResponseEntity.ok(ApiResponse.success(routeMapper.toResponseDto(route))))
                .orElseGet(() -> new ResponseEntity<>(ApiResponse.error(404, "Route not found"), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fromcity/{from_city}")
    public ResponseEntity<ApiResponse<List<RouteResponseDto>>> getRoutesByFromCity(@PathVariable String from_city) {
        List<Route> routes = routeRepository.findByFromCity(from_city);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes from " + from_city), HttpStatus.NOT_FOUND);
        }
        List<RouteResponseDto> dtos = routes.stream().map(routeMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/tocity/{to_city}")
    public ResponseEntity<ApiResponse<List<RouteResponseDto>>> getRoutesByToCity(@PathVariable String to_city) {
        List<Route> routes = routeRepository.findByToCity(to_city);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes to " + to_city), HttpStatus.NOT_FOUND);
        }
        List<RouteResponseDto> dtos = routes.stream().map(routeMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{from_city}/{to_city}")
    public ResponseEntity<ApiResponse<List<RouteResponseDto>>> getRoutesByFromAndTo(
            @PathVariable String from_city,
            @PathVariable String to_city) {
        List<Route> routes = routeRepository.findByFromCityAndToCity(from_city, to_city);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes found from " + from_city + " to " + to_city), HttpStatus.NOT_FOUND);
        }
        List<RouteResponseDto> dtos = routes.stream().map(routeMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<RouteResponseDto>> updateRoute(@PathVariable("id") Integer routeId,
                                                                     @Valid @RequestBody RouteRequestDto routeRequestDto) {
        if (!routeRepository.existsById(routeId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(404, "Route not found."));
        }

        Route updatedRoute = routeMapper.toEntity(routeRequestDto);
        updatedRoute.setId(routeId);

        Route savedRoute = routeRepository.save(updatedRoute);

        return ResponseEntity.ok(ApiResponse.success(routeMapper.toResponseDto(savedRoute)));
    }

}
