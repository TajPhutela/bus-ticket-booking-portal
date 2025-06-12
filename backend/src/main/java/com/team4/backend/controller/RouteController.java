package com.team4.backend.controller;

import com.team4.backend.dto.request.RouteRequestDto;
import com.team4.backend.dto.response.ApiResponse;
import com.team4.backend.dto.response.PagedResponse;
import com.team4.backend.dto.response.RouteResponseDto;
import com.team4.backend.entities.Route;
import com.team4.backend.mapper.RouteMapper;
import com.team4.backend.repository.RouteRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<PagedResponse<RouteResponseDto>>> getAllRoutes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Route> routePage = routeRepository.findAll(pageable);

        List<RouteResponseDto> dtos = routePage.getContent()
                .stream()
                .map(routeMapper::toResponseDto)
                .toList();

        PagedResponse<RouteResponseDto> paged = new PagedResponse<>(
                dtos, page, size, routePage.getTotalElements(),
                routePage.getTotalPages(), routePage.isLast()
        );

        return ResponseEntity.ok(ApiResponse.success(paged));
    }


    @GetMapping("/{route_id}")
    public ResponseEntity<ApiResponse<RouteResponseDto>> getRouteById(@PathVariable("route_id") int routeId) {
        return routeRepository.findById(routeId)
                .map(route -> ResponseEntity.ok(ApiResponse.success(routeMapper.toResponseDto(route))))
                .orElseGet(() -> new ResponseEntity<>(ApiResponse.error(404, "Route not found"), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/fromCity/{from_city}")
    public ResponseEntity<ApiResponse<List<RouteResponseDto>>> getRoutesByFromCity(@PathVariable("from_city") String fromCity) {
        List<Route> routes = routeRepository.findByFromCity(fromCity);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes from " + fromCity), HttpStatus.NOT_FOUND);
        }
        List<RouteResponseDto> dtos = routes.stream().map(routeMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/toCity/{to_city}")
    public ResponseEntity<ApiResponse<List<RouteResponseDto>>> getRoutesByToCity(@PathVariable("to_city") String toCity) {
        List<Route> routes = routeRepository.findByToCity(toCity);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes to " + toCity), HttpStatus.NOT_FOUND);
        }
        List<RouteResponseDto> dtos = routes.stream().map(routeMapper::toResponseDto).toList();
        return ResponseEntity.ok(ApiResponse.success(dtos));
    }

    @GetMapping("/{from_city}/{to_city}")
    public ResponseEntity<ApiResponse<List<RouteResponseDto>>> getRoutesByFromAndTo(
            @PathVariable("from_city") String fromCity,
            @PathVariable("to_city") String toCity) {
        List<Route> routes = routeRepository.findByFromCityAndToCity(fromCity, toCity);
        if (routes.isEmpty()) {
            return new ResponseEntity<>(ApiResponse.error(404, "No routes found from " + fromCity + " to " + toCity), HttpStatus.NOT_FOUND);
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
